package org.mule.extension.quadient.internal.operations.ca.fe;

import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class ConditionDto {

    @Parameter
    @Optional
    @NullSafe
    List<CategorizationConditionDto> categorizations;

    @Parameter
    @Summary("List of conditions is JSON format.")
    @Optional
    @NullSafe
    List<String> conditions;

    @Parameter
    @Optional
    @NullSafe
    List<MetadataConditionDto> metadata;


    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Metadata condition negation.")
    boolean negation;

    @Parameter
    @Summary("Logical operator (and, or).")
    LogicalOperatorDto operator;

    public List<CategorizationConditionDto> getCategorizations() {
        return categorizations;
    }

    public void setCategorizations(List<CategorizationConditionDto> categorizations) {
        this.categorizations = categorizations;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public List<MetadataConditionDto> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataConditionDto> metadata) {
        this.metadata = metadata;
    }

    public boolean isNegation() {
        return negation;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public LogicalOperatorDto getOperator() {
        return operator;
    }

    public void setOperator(LogicalOperatorDto operator) {
        this.operator = operator;
    }
}

