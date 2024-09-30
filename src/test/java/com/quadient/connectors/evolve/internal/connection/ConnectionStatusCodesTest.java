package com.quadient.connectors.evolve.internal.connection;

import com.quadient.connectors.evolve.internal.error.exception.ConnectionErrorException;
import com.quadient.connectors.evolve.internal.error.exception.NotFoundException;
import com.quadient.connectors.evolve.internal.error.exception.TooManyRequestsException;
import com.quadient.connectors.evolve.internal.error.exception.UnauthorizedException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ConnectionStatusCodesTest extends TestCase {

    static final String ENDPOINT = "/api/v1/endpoint";
    static final String API_TOKEN = "apiToken";
    static final String COMPANY_HOSTNAME = "https://example.com";
    static final int CONNECTION_TIMEOUT = 10;
    static final TimeUnit CONNECTION_TIMEOUT_UNIT = TimeUnit.MINUTES;

    @Parameterized.Parameters(name = "{index}: testStatusCodes({0})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {404, NotFoundException.class},
                {401, UnauthorizedException.class},
                {429, TooManyRequestsException.class},
                {500, ConnectionErrorException.class}
        });
    }

    private final int statusCode;
    private final Class<? extends Exception> expectedException;

    private HttpClient httpClient;

    private TransformationService transformationService;

    public ConnectionStatusCodesTest(int statusCode, Class<? extends Exception> expectedException) {
        this.statusCode = statusCode;
        this.expectedException = expectedException;
        httpClient = Mockito.mock(HttpClient.class);
        transformationService = Mockito.mock(TransformationService.class);
    }

    Connection connection;

    @Override
    @Before
    public void setUp() {
        connection = new Connection(httpClient, COMPANY_HOSTNAME, API_TOKEN, transformationService, CONNECTION_TIMEOUT, CONNECTION_TIMEOUT_UNIT);
    }

    @Test
    public void testStatusCodes() throws IOException, TimeoutException {
        when(httpClient.send(any(HttpRequest.class), any(HttpRequestOptions.class))).thenReturn(new TestHttpResponse(statusCode));
        try {
            connection.sendPOSTRequest(ENDPOINT, "body");
            fail("Expected exception: " + expectedException.getName());
        } catch (Exception e) {
            assertTrue(expectedException.isInstance(e));
        }
    }
}
