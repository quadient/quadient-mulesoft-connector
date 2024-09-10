package com.quadient.connectors.evolve.internal.connection.provider;

import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.generated.model.v6.batch.QueryAppHealth;
import org.mule.runtime.api.lifecycle.InitialisationException;
import org.mule.runtime.api.lifecycle.Startable;
import org.mule.runtime.api.lifecycle.Stoppable;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.client.proxy.ProxyConfig;
import org.mule.sdk.api.annotation.param.ConfigOverride;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.RefName;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Placement;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.connectivity.CachedConnectionProvider;
import org.mule.sdk.api.connectivity.ConnectionValidationResult;
import org.mule.sdk.api.exception.ModuleException;

import javax.inject.Inject;

import java.util.concurrent.TimeUnit;

public class ConnectionProvider implements
        CachedConnectionProvider<Connection>, Startable, Stoppable {

    @DisplayName("Quadient company hostname")
    @Example("https://company.quadient.com")
    @Summary("The hostname of the Quadient company")
    @Parameter
    private String companyHostname;

    @DisplayName("Api token")
    @Parameter
    @Summary("Bearer token for authenticating your request. You need to generate an API key for a user role with relevant permissions in Settings | Aministration | API Keys.")
    private String apiToken;


    @Parameter
    @Summary("Type of the API key. It will be used for validation of connection.")
    private ApplicationType applicationType;

    @ConfigOverride
    @Parameter
    @Optional(defaultValue = "30")
    @Placement(tab = Placement.ADVANCED_TAB, order = 1)
    @DisplayName("Timeout")
    private int connectionTimeout;

    @ConfigOverride
    @Parameter
    @Optional(defaultValue = "SECONDS")
    @DisplayName("Timeout unit")
    @Placement(tab = Placement.ADVANCED_TAB, order = 2)
    private TimeUnit connectionTimeoutUnit;

    @Inject
    private HttpService httpService;

    @Inject
    private TransformationService transformationService;

    @RefName
    private String configName;

    @Parameter
    @Optional
    @Summary("Reusable configuration element for outbound connections through a proxy")
    @Placement(tab = "Proxy")
    private ProxyConfig proxyConfig;

    private HttpClient httpClient;

    @Override
    public void start() throws InitialisationException {
        httpClient = httpService.getClientFactory().
                create(new HttpClientConfiguration.Builder()
                        .setName(configName)
                        .setProxyConfig(proxyConfig)
                        .build());
        httpClient.start();
    }

    @Override
    public void stop() {
        if (httpClient != null) {
            httpClient.stop();
        }
    }

    @Override
    public Connection connect() {
        return new Connection(httpClient, companyHostname, apiToken, transformationService, connectionTimeout, connectionTimeoutUnit);
    }

    @Override
    public void disconnect(Connection httpConnection) {
        if (httpClient != null) {
            httpClient.stop();
        }
    }

    @Override
    public ConnectionValidationResult validate(Connection httpConnection) {
        String endpoint = null;
        String body = null;
        HttpConstants.Method method;

        switch (applicationType) {
            case GENERATE_ON_DEMAND: {
                endpoint = ServiceEndpoint.GENERATE_APP_HEALTH;
                body = new ObjectConverter().convertToJson(new QueryAppHealth().application(QueryAppHealth.ApplicationEnum.GENERATEONDEMAND));
                method = HttpConstants.Method.POST;
                break;
            }
            case GENERATE_BATCH: {
                endpoint = ServiceEndpoint.GENERATE_APP_HEALTH;
                body = new ObjectConverter().convertToJson(new QueryAppHealth().application(QueryAppHealth.ApplicationEnum.GENERATEBATCH));
                method = HttpConstants.Method.POST;
                break;
            }
            case CONTENT_AUTHOR:
            case FRONT_OFFICE: {
                endpoint = ServiceEndpoint.CA_HEALTH;
                method = HttpConstants.Method.GET;
                break;
            }
            default: {
                return ConnectionValidationResult.failure("Invalid application type", new Exception("Invalid application type"));
            }
        }
        try {
            httpConnection.sendRequest(method, endpoint, body, null);
            return ConnectionValidationResult.success();
        } catch (ModuleException e) {
            return ConnectionValidationResult.failure("Connection failed", e);
        }
    }

}