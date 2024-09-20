package com.quadient.connectors.evolve.api.generate.batch;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

public class BatchJobStatusInputFE {
    @Parameter
    @Summary("Unique identifier of the batch job.")
    public String batchJobId;

    public String getBatchJobId() {
        return batchJobId;
    }
}
