package com.quadient.connectors.evolve.internal.operation.generatev6.batch;

import com.quadient.connectors.evolve.api.generate.batch.StartBatchJobInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.error.provider.ExecuteErrorsProvider;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.generated.model.v6.batch.BatchVariableWithTypeEnterpriseDto;
import com.quadient.connectors.generated.model.v6.batch.StartBatchJobRequest;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.sdk.api.annotation.param.ParameterGroup;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;


public class StartBatchJobOperation {
    static final String ENDPOINT = ServiceEndpoint.BATCH_START_BATCH_JOB;

    @OutputJsonType(schema = "jsonSchema/batch_startBatchJobOperation.json")
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts a batch job using a defined processing pipeline")
    @DisplayName("Batch - Start Batch Job")
    public Result<InputStream, HttpResponseAttributes> batchStartBatchJob(
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,
            @ParameterGroup(name = "Start Batch Job") StartBatchJobInputFE input
    ) {
        if (input.getPriority() != null && (input.getPriority() < 1 || input.getPriority() > 100)) {
            throw new InvalidInputParameterException(new Exception("Priority must be between 1 and 100"));
        }
        if (input.getStartBatchJobVariables() != null && input.getStartBatchJobVariables().size() > 50) {
            throw new InvalidInputParameterException(new Exception("Variables must be less than 50"));
        }

        StartBatchJobRequest request = new StartBatchJobRequest();
        request.setPipelineName(input.getPipelineName());

        if (input.getWorkingFolderId() != null && !input.getWorkingFolderId().isEmpty()) {
            request.setWorkingFolderId(input.getWorkingFolderId());
        }

        if (input.getDescription() != null && !input.getDescription().isEmpty()) {
            request.setDescription(input.getDescription());
        }
        if (input.getPriority() != null) {
            request.setPriority(input.getPriority());
        }
        if (input.getExpiration() != null) {
            request.setExpiration(input.getExpiration());
        }
        request.setUseDraftPipeline(input.isUseDraftPipeline());
        request.setUseDraftResources(input.isUseDraftResources());
        if (input.getStartBatchJobVariables() != null) {
            input.getStartBatchJobVariables().forEach(v -> request.addVariablesItem(new BatchVariableWithTypeEnterpriseDto()
                    .type(BatchVariableWithTypeEnterpriseDto.TypeEnum.fromValue(v.getInputVariablesType().getValue()))
                    .codeName(v.getCodeName())
                    .value(v.getValue())
                    .options(v.getInputVariablesOptions())));
        }

        if (input.getStartBatchJobAttachments() != null && !input.getStartBatchJobAttachments().isEmpty()) {
            return connection.sendRequestMultiPart(HttpConstants.Method.POST, ENDPOINT, new ObjectConverter().convertToJson(request), input.getStartBatchJobAttachments());
        }

        return connection.sendPOSTRequest(ENDPOINT, new ObjectConverter().convertToJson(request));
    }
}
