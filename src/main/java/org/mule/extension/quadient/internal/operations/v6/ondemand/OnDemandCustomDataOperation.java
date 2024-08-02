package org.mule.extension.quadient.internal.operations.v6.ondemand;

import org.mule.extension.quadient.internal.Configuration;
import org.mule.extension.quadient.internal.Connection;
import org.mule.extension.quadient.internal.errors.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.errors.exception.InvalidInputParameterException;
import org.mule.extension.quadient.internal.operations.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operations.ServiceEndpoint;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.Content;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.HashMap;

public class OnDemandCustomDataOperation {

    final String endpoint = ServiceEndpoint.ON_DEMAND_ON_DEMAND_CUSTOM_DATA;

    @MediaType(MediaType.ANY)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts and processes an on-demand job (using a defined processing pipeline) and uploads custom data to an internal location (these data files are later automatically deleted).\n" +
            "Response example:\n" +
            "{\n" +
            "  \"id\": \"0f8fad5b-d9cb-469f-a165-70867728950e\",\n" +
            "  \"status\": \"Success\",\n" +
            "  \"warnings\": [\n" +
            "    {\n" +
            "      \"errorMessage\": \"string\"\n" +
            "    }\n" +
            "  ]\n" +
            "}")
    @DisplayName("OnDemand - OnDemand Custom Data")
    public Result<InputStream, HttpResponseAttributes> OnDemandOnDemandCustomData(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:\"Folder/NestedFolder/pipelineName\".")
            String pipelineName,

            @Optional
            @Summary("Gives a custom name to the data file.")
            String fileName,

            @Optional
            @Summary("Data file is saved to the specified folder in the working folder | input directory.")
            String folder,

            @Optional
            @NullSafe
            @DisplayName("Variables")
            @Summary("List of pipeline variables used for the job. The maximum number of these variables is 50.")
            MultiMap<String, String> pipelineVariables,

            @Optional(defaultValue = "false")
            @Summary("If true, the job will be run using a draft version of the processing pipeline.")
            boolean useDraftPipeline,

            @Optional(defaultValue = "false")
            @Summary("If true, the job will be run using a draft version of the relevant resources (scripts, connectors).")
            boolean useDraftResources,

            @Content(primary = true)
            TypedValue<Object> customData

    ) {
        if (pipelineVariables != null && pipelineVariables.size() > 50) {
            throw new InvalidInputParameterException(new Exception("The number of variables cannot exceed 50."));
        }

        HashMap<String, String> headers = new HashMap<>();
        if (fileName!= null){
            headers.put("filename", fileName);
        }
        if (folder!= null){
            headers.put("folder", folder);
        }
        
        headers.put("useDraftPipeline", Boolean.toString(useDraftPipeline));
        headers.put("useDraftResources", Boolean.toString(useDraftResources));
        pipelineVariables.forEach((key, value) -> headers.put("c-variable-" + key, value));
        
        return connection.sendPOSTRequest(endpoint + "/" + pipelineName, customData, headers);
    }
}
