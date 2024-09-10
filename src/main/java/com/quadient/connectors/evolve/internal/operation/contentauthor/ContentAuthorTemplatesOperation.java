package com.quadient.connectors.evolve.internal.operation.contentauthor;

import com.quadient.connectors.evolve.api.contentauthor.CategorizationConditionFE;
import com.quadient.connectors.evolve.api.contentauthor.ConditionFE;
import com.quadient.connectors.evolve.api.contentauthor.ContentAuthorTemplatesInputFE;
import com.quadient.connectors.evolve.api.contentauthor.MetadataConditionFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.error.provider.ExecuteErrorsProvider;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.generated.model.ca.CategorizationCondition;
import com.quadient.connectors.generated.model.ca.Condition;
import com.quadient.connectors.generated.model.ca.MetadataCondition;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.sdk.api.annotation.param.Config;
import org.mule.sdk.api.annotation.param.ParameterGroup;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.HashMap;

public class ContentAuthorTemplatesOperation {
    static final String ENDPOINT = ServiceEndpoint.CA_TEMPLATES;

    @OutputJsonType(schema = "jsonSchema/ca_contentAuthorTemplatesOperation.json")
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Lists templates.")
    @DisplayName("Content Author - Get Templates")
    public Result<InputStream, HttpResponseAttributes> contentAuthorGetTemplates(
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,
            @ParameterGroup(name = "Get Templates") ContentAuthorTemplatesInputFE input
    ) {
        if (input.getLimit() > 100) {
            throw new InvalidInputParameterException(new Exception("The number of items to return cannot exceed 100."));
        }

        HashMap<String, String> uriParams = new HashMap<>();
        if (input.getFolder() != null && !input.getFolder().isEmpty()) {
            uriParams.put("folder", input.getFolder());
        }
        uriParams.put("offset", String.valueOf(input.getOffset()));
        uriParams.put("limit", String.valueOf(input.getLimit()));
        uriParams.put("includeMetadata", String.valueOf(input.isIncludeMetadata()));
        if (input.getHolder() != null && !input.getHolder().isEmpty()) {
            uriParams.put("holder", input.getHolder());
        }
        if (input.getApprovalStates() != null && !input.getApprovalStates().isEmpty()) {
            uriParams.put("approvalStates", input.getApprovalStates());
        }
        if (input.getCondition() != null) {
            uriParams.put("condition", new ObjectConverter().convertToJson(convertCondition(input.getCondition())));
        }

        return connection.sendRequest(HttpConstants.Method.GET, ENDPOINT, null, uriParams);
    }

    private Condition convertCondition(ConditionFE condition) {
        Condition resultCondition = new Condition();
        resultCondition.setNegation(condition.isNegation());
        resultCondition.setOperator(Condition.OperatorEnum.fromValue(condition.getOperator().getValue()));

        if (condition.getMetadata() != null) {
            for (MetadataConditionFE metadataConditionFE : condition.getMetadata()) {
                MetadataCondition metadataCondition = new MetadataCondition();
                metadataCondition.setName(metadataConditionFE.getName());
                metadataCondition.setNegation(metadataConditionFE.isNegation());
                metadataCondition.setOperator(MetadataCondition.OperatorEnum.fromValue(metadataConditionFE.getOperator().getValue()));
                metadataCondition.setValue(metadataConditionFE.getValue());
                resultCondition.addMetadataItem(metadataCondition);
            }
        }

        if (condition.getCategorizations() != null) {
            for (CategorizationConditionFE categorizationConditionFE : condition.getCategorizations()) {
                CategorizationCondition categorizationCondition = new CategorizationCondition();
                categorizationCondition.setFieldName(categorizationConditionFE.getFieldName());
                categorizationCondition.setName(categorizationConditionFE.getName());
                categorizationCondition.setNegation(categorizationConditionFE.isNegation());
                categorizationCondition.setOperator(CategorizationCondition.OperatorEnum.fromValue(categorizationConditionFE.getOperator().getValue()));
                categorizationCondition.setValue(categorizationConditionFE.getValue());
                resultCondition.addCategorizationsItem(categorizationCondition);
            }
        }
        if (condition.getConditions() != null) {
            for (String conditionJSON : condition.getConditions()) {
                resultCondition.addConditionsItem(new ObjectConverter().readValue(conditionJSON, Condition.class));
            }
        }
        return resultCondition;
    }
}
