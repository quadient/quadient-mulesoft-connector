package org.mule.extension.quadient.internal.operations.ca;

import com.quadient.mule.model.ca.CategorizationCondition;
import com.quadient.mule.model.ca.Condition;
import com.quadient.mule.model.ca.MetadataCondition;
import org.mule.extension.quadient.internal.Configuration;
import org.mule.extension.quadient.internal.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.errors.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.errors.exception.InvalidInputParameterException;
import org.mule.extension.quadient.internal.operations.ServiceEndpoint;
import org.mule.extension.quadient.internal.operations.ca.fe.*;
import org.mule.extension.quadient.internal.operations.ca.fe.*;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;

import java.io.InputStream;
import java.util.HashMap;


public class ContentAuthorTemplatesOperation {
    final String endpoint = ServiceEndpoint.CA_TEMPLATES;

    @MediaType(MediaType.APPLICATION_JSON)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Lists templates.")
    @DisplayName("Content Author - Get Templates")
    public InputStream contentAuthorGetTemplates(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Optional
            @Summary("Name of folder whose content will be listed.")
            String folder,

            @Optional
            @Summary("Number of items to skip before starting to collect the resulting.")
            int offset,

            @Optional(defaultValue = "20")
            @Summary("Number of items to return (max. 100).")
            int limit,

            @Optional(defaultValue = "false")
            @Summary("Determines whether to include metadata in the response.")
            boolean includeMetadata,

            @Optional
            @Summary("List templates that the specified user can see.")
            String holder,

            @Optional
            @Example("Production:Testing")
            @Summary("List templates that have the specified approval states, separated by a colon.")
            String approvalStates,

            @Optional
            @Summary("Conditions can be nested and can contain the same elements as the main condition.")
            ConditionDto condition
    ) {
        if (limit > 100) {
            throw new InvalidInputParameterException(new Exception("The number of items to return cannot exceed 100."));
        }

        HashMap<String, String> uriParams = new HashMap<>();
        if (folder != null && !folder.isEmpty()) {
            uriParams.put("folder", folder);
        }
        uriParams.put("offset", String.valueOf(offset));
        uriParams.put("limit", String.valueOf(limit));
        uriParams.put("includeMetadata", String.valueOf(includeMetadata));
        if (holder != null && !holder.isEmpty()) {
            uriParams.put("holder", holder);
        }
        if (approvalStates != null && !approvalStates.isEmpty()) {
            uriParams.put("approvalStates", approvalStates);
        }
        if (condition != null) {
            uriParams.put("condition", new ObjectConverter().convertToJson(convertCondition(condition)));
        }

        return connection.sendRequest(HttpConstants.Method.GET, endpoint, null, uriParams);
    }

    private Condition.OperatorEnum convertLogicalOperatorDto(LogicalOperatorDto operator) {
        for (Condition.OperatorEnum en : Condition.OperatorEnum.values()) {
            if (en.getValue().toLowerCase().equals(operator.name().toLowerCase())) {
                return en;
            }
        }
        return null;
    }

    private MetadataCondition.OperatorEnum convertOperatorEnumDtoToMetadataEnum(OperatorEnumDto operator) {
        for (MetadataCondition.OperatorEnum en : MetadataCondition.OperatorEnum.values()) {
            if (en.getValue().toLowerCase().equals(operator.name().toLowerCase())) {
                return en;
            }
        }
        return null;
    }

    private CategorizationCondition.OperatorEnum convertOperatorEnumDtoToCategorizationEnum(OperatorEnumDto operator) {
        for (CategorizationCondition.OperatorEnum en : CategorizationCondition.OperatorEnum.values()) {
            if (en.getValue().toLowerCase().equals(operator.name().toLowerCase())) {
                return en;
            }
        }
        return null;
    }

    private Condition convertCondition(ConditionDto condition) {
        Condition resultCondition = new Condition();
        resultCondition.setNegation(condition.isNegation());
        resultCondition.setOperator(convertLogicalOperatorDto(condition.getOperator()));

        if (condition.getMetadata() != null) {
            for (MetadataConditionDto metadataConditionDto : condition.getMetadata()) {
                MetadataCondition metadataCondition = new MetadataCondition();
                metadataCondition.setName(metadataConditionDto.getName());
                metadataCondition.setNegation(metadataConditionDto.isNegation());
                metadataCondition.setOperator(convertOperatorEnumDtoToMetadataEnum(metadataConditionDto.getOperator()));
                metadataCondition.setValue(metadataConditionDto.getValue());
                resultCondition.addMetadataItem(metadataCondition);
            }
        }

        if (condition.getCategorizations() != null) {
            for (CategorizationConditionDto categorizationConditionDto : condition.getCategorizations()) {
                CategorizationCondition categorizationCondition = new CategorizationCondition();
                categorizationCondition.setFieldName(categorizationConditionDto.getFieldName());
                categorizationCondition.setName(categorizationConditionDto.getName());
                categorizationCondition.setNegation(categorizationConditionDto.isNegation());
                categorizationCondition.setOperator(convertOperatorEnumDtoToCategorizationEnum(categorizationConditionDto.getOperator()));
                categorizationCondition.setValue(categorizationConditionDto.getValue());
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
