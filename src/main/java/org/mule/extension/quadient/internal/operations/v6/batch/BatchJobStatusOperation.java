package org.mule.extension.quadient.internal.operations.v6.batch;

import com.quadient.mule.model.v6.batch.BatchStatusJobResponse;
import com.quadient.mule.model.v6.batch.QueryBatchJobStatus;
import org.mule.extension.quadient.internal.Configuration;
import org.mule.extension.quadient.internal.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.errors.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.operations.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operations.ServiceEndpoint;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.MediaType;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;


public class BatchJobStatusOperation {
    final String endpoint = ServiceEndpoint.BATCH_BATCH_JOB_STATUS;

    @MediaType(MediaType.TEXT_PLAIN)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Returns the current status of the specified batch job.\n" +
            "States are:\n" +
            "'WaitingForProcessing',\n" +
            "'Preprocessing',\n" +
            "'Processing',\n" +
            "'Finished',\n" +
            "'Failed',\n" +
            "'Unknown',\n" +
            "'Stopping',\n" +
            "'Stopped',\n" +
            "'TimedOut',\n" +
            "'Skipped',\n" +
            "'PartiallyFinished',\n" +
            "'Running'")
    @DisplayName("Batch - Batch Job Status")
    public Result<String, HttpResponseAttributes> batchBatchJobStatus(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Summary("Unique identifier of the batch job.")
            String batchJobId
    ) {
        QueryBatchJobStatus queryBatchJobStatus = new QueryBatchJobStatus().batchJobId(batchJobId);
        Result<InputStream, HttpResponseAttributes> result = connection.sendPOSTRequest(endpoint, new ObjectConverter().convertToJson(queryBatchJobStatus));
        BatchStatusJobResponse responseObj = new ObjectConverter().readValue(result.getOutput(), BatchStatusJobResponse.class);
        return Result.<String, HttpResponseAttributes>builder()
                .output(responseObj.getBatchJob().getState().getValue())
                .attributes(result.getAttributes().get())
                .mediaType(org.mule.runtime.api.metadata.MediaType.TEXT)
                .build();
    }
}
