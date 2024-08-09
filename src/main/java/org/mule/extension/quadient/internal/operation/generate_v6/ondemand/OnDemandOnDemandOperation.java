package org.mule.extension.quadient.internal.operation.generate_v6.ondemand;

import com.quadient.mule.model.v6.ondemand.BatchVariableWithTypeEnterpriseDto;
import com.quadient.mule.model.v6.ondemand.OnDemandPipelineRequest;
import org.mule.extension.quadient.internal.config.Configuration;
import org.mule.extension.quadient.internal.connection.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.error.provider.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.error.exception.InvalidInputParameterException;
import org.mule.extension.quadient.internal.operation.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operation.ServiceEndpoint;
import org.mule.extension.quadient.api.generate.InputVariablesOptionsFE;
import org.mule.extension.quadient.api.generate.MultipartAttachmentFE;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.Config;
import org.mule.sdk.api.annotation.param.MediaType;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.List;

public class OnDemandOnDemandOperation {
    static final String ENDPOINT = ServiceEndpoint.ON_DEMAND_ON_DEMAND;

    @MediaType(MediaType.ANY)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts and processes an on-demand job using a defined processing pipeline.")
    @DisplayName("OnDemand - OnDemand")
    public Result<InputStream, HttpResponseAttributes> onDemandOnDemand(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:'Folder/NestedFolder/pipelineName'.")
            String pipelineName,

            @Optional
            @NullSafe
            @Summary("List of processing pipeline variables. It can be used to override values of existing variables in the given processing pipeline. E.g. when a variable is used in the pipeline&#39;s output path, by defining a different value for the same codeName, you can easily change the output path as you start the pipeline without having to re-configure the pipeline itself.")
            List<InputVariablesOptionsFE> variables,

            @Optional(defaultValue = "false")
            @Summary("If true, the job will be run using a draft version of the processing pipeline.")
            boolean useDraftPipeline,

            @Optional(defaultValue = "false")
            @Summary("If true, the job will be run using a draft version of the relevant resources (scripts, connectors).")
            boolean useDraftResources,

            @Optional
            @NullSafe
            List<MultipartAttachmentFE> attachments
    ) {
        if (variables != null && variables.size() > 50) {
            throw new InvalidInputParameterException(new Exception("The number of variables cannot exceed 50."));
        }

        OnDemandPipelineRequest request = new OnDemandPipelineRequest();
        request.setPipelineName(pipelineName);
        request.setUseDraftPipeline(useDraftPipeline);
        request.setUseDraftResources(useDraftResources);
        if (variables != null) {
            variables.forEach(v -> request.addVariablesItem(new BatchVariableWithTypeEnterpriseDto()
                    .type(BatchVariableWithTypeEnterpriseDto.TypeEnum.fromValue(v.getType().toString()))
                    .codeName(v.getCodeName())
                    .value(v.getValue())
                    .options(v.getOptions())));
        }
        
        if (attachments != null && !attachments.isEmpty()) {
            return connection.sendRequestMultiPart(HttpConstants.Method.POST, ENDPOINT, new ObjectConverter().convertToJson(request), attachments);
        }

        return connection.sendPOSTRequest(ENDPOINT, new ObjectConverter().convertToJson(request));
    }
}
