package com.quadient.connectors.evolve.api.contentauthor;

import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class ConditionFE {

    @Parameter
    @Optional
    @NullSafe
    List<CategorizationConditionFE> categorizations;

    @Parameter
    @Summary("List of conditions is JSON format.")
    @Optional
    @NullSafe
    List<String> conditions;

    @Parameter
    @Optional
    @NullSafe
    List<MetadataConditionFE> metadata;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Metadata condition negation.")
    boolean negation;

    @Parameter
    @Summary("Operator (and, or).")
    LogicalOperatorFE operator;

    public List<CategorizationConditionFE> getCategorizations() {
        return categorizations;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public List<MetadataConditionFE> getMetadata() {
        return metadata;
    }

    public boolean isNegation() {
        return negation;
    }

    public LogicalOperatorFE getOperator() {
        return operator;
    }
}
