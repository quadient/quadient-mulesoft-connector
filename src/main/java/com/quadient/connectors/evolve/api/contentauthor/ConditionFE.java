package com.quadient.connectors.evolve.api.contentauthor;

import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class ConditionFE {

    @Parameter
    @Optional
    @DisplayName("Categorizations")
    @NullSafe
    public List<CategorizationConditionFE> contentAuthorConditionCategorizations;

    @Parameter
    @Summary("List of conditions is JSON format.")
    @Optional
    @NullSafe
    public List<String> conditions;

    @Parameter
    @Optional
    @DisplayName("Metadata")
    @NullSafe
    public List<MetadataConditionFE> contentAuthorConditionMetadata;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Metadata condition negation.")
    public boolean negation;

    @Parameter
    @Summary("Operator (and, or).")
    @DisplayName("Operator")
    public LogicalOperatorFE contentAuthorConditionOperator;

    public List<CategorizationConditionFE> getContentAuthorConditionCategorizations() {
        return contentAuthorConditionCategorizations;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public List<MetadataConditionFE> getContentAuthorConditionMetadata() {
        return contentAuthorConditionMetadata;
    }

    public boolean isNegation() {
        return negation;
    }

    public LogicalOperatorFE getContentAuthorConditionOperator() {
        return contentAuthorConditionOperator;
    }
}
