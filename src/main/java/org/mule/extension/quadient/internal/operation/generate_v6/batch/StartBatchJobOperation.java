package org.mule.extension.quadient.internal.operation.generate_v6.batch;

import com.quadient.mule.model.v6.batch.BatchVariableWithTypeEnterpriseDto;
import com.quadient.mule.model.v6.batch.StartBatchJobRequest;
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
import org.mule.sdk.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.sdk.api.annotation.param.Config;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.List;


public class StartBatchJobOperation {
    static final String ENDPOINT = ServiceEndpoint.BATCH_START_BATCH_JOB;

    @OutputJsonType(schema = "jsonSchema/batch_startBatchJobOperation.json")
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts a batch job using a defined processing pipeline")
    @DisplayName("Batch - Start batch job")
    public Result<InputStream, HttpResponseAttributes> batchStartBatchJob(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:'Folder/NestedFolder/pipelineName'.")
            String pipelineName,

            @Summary("Unique identifier of an existing job working folder. If a different working folder is set in the pipeline, the folder specified here takes priority.")
            String workingFolderId,

            @Summary("Custom description of the job.")
            String description,

            @Optional
            @NullSafe
            @Summary("List of processing pipeline variables. It can be used to override values of existing variables in the given processing pipeline. E.g. when a variable is used in the pipeline&#39;s output path, by defining a different value for the same codeName, you can easily change the output path as you start the pipeline without having to re-configure the pipeline itself.")
            List<InputVariablesOptionsFE> variables,

            @Optional
            @Summary("Specifies the job priority. Jobs with higher priority are run first. The  value set here overwrites any priority set when designing the pipeline.\n" +
                    "   * minimum: 1\n" +
                    "   * maximum: 100")
            Long priority,

            @Optional
            @Summary("Date and time  the batch expires (date/time format according to the ISO 8601 standard). Once expired, the job is deleted. If undefined, the job will expire in 90 days.")
            @Example("2024-07-10T10:15:30+01:00")
            String expiration,

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
        if (priority != null && (priority < 1 || priority > 100)) {
            throw new InvalidInputParameterException(new Exception("Priority must be between 1 and 100"));
        }
        if (variables != null && variables.size() > 50) {
            throw new InvalidInputParameterException(new Exception("Variables must be less than 50"));
        }

        StartBatchJobRequest request = new StartBatchJobRequest();
        request.setPipelineName(pipelineName);
        request.setWorkingFolderId(workingFolderId);
        if (description != null && !description.isEmpty()) {
            request.setDescription(description);
        }
        if (priority != null) {
            request.setPriority(priority);
        }
        if (expiration != null) {
            request.setExpiration(expiration);
        }
        request.setUseDraftPipeline(useDraftPipeline);
        request.setUseDraftResources(useDraftResources);
        if (variables != null){
            variables.forEach(v -> request.addVariablesItem(new BatchVariableWithTypeEnterpriseDto()
                    .type( BatchVariableWithTypeEnterpriseDto.TypeEnum.fromValue(v.getType().getValue()))
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
