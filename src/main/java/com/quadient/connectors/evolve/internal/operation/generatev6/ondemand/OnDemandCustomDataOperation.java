package com.quadient.connectors.evolve.internal.operation.generatev6.ondemand;

import com.quadient.connectors.evolve.api.generate.ondemand.OnDemandCustomDataInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.error.provider.ExecuteErrorsProvider;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.MediaType;
import org.mule.sdk.api.annotation.param.ParameterGroup;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.HashMap;

public class OnDemandCustomDataOperation {

    static final String ENDPOINT = ServiceEndpoint.ON_DEMAND_ON_DEMAND_CUSTOM_DATA;

    @MediaType(MediaType.ANY)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts and processes an on-demand job (using a defined processing pipeline) and uploads custom data to an internal location (these data files are later automatically deleted).")
    @DisplayName("OnDemand - OnDemand Custom Data")
    public Result<InputStream, HttpResponseAttributes> onDemandOnDemandCustomData(
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,
            @ParameterGroup(name = "OnDemand Custom Data") OnDemandCustomDataInputFE input
    ) {
        if (input.getOnDemandCustomDataPipelineVariables() != null && input.getOnDemandCustomDataPipelineVariables().size() > 50) {
            throw new InvalidInputParameterException(new Exception("The number of variables cannot exceed 50."));
        }

        HashMap<String, String> headers = new HashMap<>();
        if (input.getFileName() != null) {
            headers.put("filename", input.getFileName());
        }
        if (input.getFolder() != null) {
            headers.put("folder", input.getFolder());
        }

        headers.put("useDraftPipeline", Boolean.toString(input.isUseDraftPipeline()));
        headers.put("useDraftResources", Boolean.toString(input.isUseDraftResources()));
        if (input.getOnDemandCustomDataPipelineVariables() != null) {
            input.getOnDemandCustomDataPipelineVariables().forEach((key, value) -> headers.put("c-variable-" + key, value));
        }
        
        return connection.sendPOSTRequest(ENDPOINT + "/" + input.getPipelineName(), input.getOnDemandCustomDataCustomData(), headers);
    }
}
