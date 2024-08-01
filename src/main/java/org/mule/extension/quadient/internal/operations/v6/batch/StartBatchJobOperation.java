package org.mule.extension.quadient.internal.operations.v6.batch;

import com.quadient.mule.model.v6.batch.BatchVariableWithTypeEnterpriseDto;
import com.quadient.mule.model.v6.batch.StartBatchJobRequest;
import org.mule.extension.quadient.internal.Configuration;
import org.mule.extension.quadient.internal.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.errors.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.errors.exception.InvalidInputParameterException;
import org.mule.extension.quadient.internal.operations.ServiceEndpoint;
import org.mule.extension.quadient.internal.operations.v6.fe.InputVariablesOptionsFE;
import org.mule.extension.quadient.internal.operations.v6.fe.MultipartAttachmentFE;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.display.DisplayName;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class StartBatchJobOperation {
    final String endpoint = ServiceEndpoint.BATCH_START_BATCH_JOB;

    @MediaType(MediaType.APPLICATION_JSON)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts a batch job using a defined processing pipeline.\n" +
            "Response example:\n" +
            "{\n" +
            "    \"workingFolderId\": \"string\",\n" +
            "    \"batchJobId\": \"string\",\n" +
            "    \"eventId\": 0,\n" +
            "    \"time\": \"2019-08-24T14:15:22Z\",\n" +
            "    \"warnings\": [{\n" +
            "            \"errorMessage\": \"string\"\n" +
            "        }\n" +
            "    ]\n" +
            "}")
    @DisplayName("Batch - Start batch job")
    public InputStream batchStartBatchJob(
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

        variables.forEach(v -> {
            List<String> options = Arrays.asList(v.getOptions());
            request.addVariablesItem(new BatchVariableWithTypeEnterpriseDto()
                    .type( BatchVariableWithTypeEnterpriseDto.TypeEnum.fromValue(v.getType().getValue()))
                    .codeName(v.getCodeName())
                    .value(v.getValue())
                    .options(options));
        });

        if (attachments != null && !attachments.isEmpty()) {
            return connection.sendRequestMultiPart(HttpConstants.Method.POST, endpoint, new ObjectConverter().convertToJson(request), attachments);
        }

        return connection.sendPOSTRequest(endpoint, new ObjectConverter().convertToJson(request));
    }
}
