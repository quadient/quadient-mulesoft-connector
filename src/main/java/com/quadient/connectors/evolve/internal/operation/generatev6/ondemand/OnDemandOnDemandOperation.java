package com.quadient.connectors.evolve.internal.operation.generatev6.ondemand;

import com.quadient.connectors.evolve.api.generate.ondemand.OnDemandOnDemandInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.error.provider.ExecuteErrorsProvider;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.generated.model.v6.ondemand.BatchVariableWithTypeEnterpriseDto;
import com.quadient.connectors.generated.model.v6.ondemand.OnDemandPipelineRequest;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.MediaType;
import org.mule.sdk.api.annotation.param.ParameterGroup;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;

public class OnDemandOnDemandOperation {
    static final String ENDPOINT = ServiceEndpoint.ON_DEMAND_ON_DEMAND;

    @MediaType(MediaType.ANY)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Starts and processes an on-demand job using a defined processing pipeline.")
    @DisplayName("On Demand - Start On-Demand Job")
    public Result<InputStream, HttpResponseAttributes> onDemandOnDemand(
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,
            @ParameterGroup(name = "Start On-Demand Job") OnDemandOnDemandInputFE input
    ) {
        if (input.getOnDemandOnDemandVariables() != null && input.getOnDemandOnDemandVariables().size() > 50) {
            throw new InvalidInputParameterException(new Exception("The number of variables cannot exceed 50."));
        }

        OnDemandPipelineRequest request = new OnDemandPipelineRequest();
        request.setPipelineName(input.getPipelineName());
        request.setUseDraftPipeline(input.isUseDraftPipeline());
        request.setUseDraftResources(input.isUseDraftResources());
        if (input.getOnDemandOnDemandVariables() != null) {
            input.getOnDemandOnDemandVariables().forEach(v -> request.addVariablesItem(new BatchVariableWithTypeEnterpriseDto()
                    .type(BatchVariableWithTypeEnterpriseDto.TypeEnum.fromValue(v.getInputVariablesType().toString()))
                    .codeName(v.getCodeName())
                    .value(v.getValue())
                    .options(v.getInputVariablesOptions())));
        }

        if (input.getOnDemandOnDemandAttachments() != null && !input.getOnDemandOnDemandAttachments().isEmpty()) {
            return connection.sendRequestMultiPart(HttpConstants.Method.POST, ENDPOINT, new ObjectConverter().convertToJson(request), input.getOnDemandOnDemandAttachments());
        }

        return connection.sendPOSTRequest(ENDPOINT, new ObjectConverter().convertToJson(request));
    }
}
