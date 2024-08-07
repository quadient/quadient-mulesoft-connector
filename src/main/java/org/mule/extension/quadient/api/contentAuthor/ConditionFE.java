package org.mule.extension.quadient.api.contentAuthor;

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
    @Summary("Logical operator (and, or).")
    LogicalOperatorFE operator;

    public List<CategorizationConditionFE> getCategorizations() {
        return categorizations;
    }

    public void setCategorizations(List<CategorizationConditionFE> categorizations) {
        this.categorizations = categorizations;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    public List<MetadataConditionFE> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataConditionFE> metadata) {
        this.metadata = metadata;
    }

    public boolean isNegation() {
        return negation;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public LogicalOperatorFE getOperator() {
        return operator;
    }

    public void setOperator(LogicalOperatorFE operator) {
        this.operator = operator;
    }
}

