package org.mule.extension.quadient.internal.operations.ca.fe;

import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

public class CategorizationConditionDto {
    @Parameter
    @Summary("Condition field name.")
    String fieldName;

    @Parameter
    @Summary("Condition name.")
    String name;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Condition negation.")
    boolean negation;
    
    @Parameter
    @Summary("Occurrence operator.")
    OperatorEnumDto operator;
    
    @Parameter
    @Summary("Condition value.")
    String value;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNegation() {
        return negation;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public OperatorEnumDto getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnumDto operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


