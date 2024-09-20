package com.quadient.connectors.evolve.api.contentauthor;

import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

public class CategorizationConditionFE {
    @Parameter
    @Summary("Condition field name.")
    public String fieldName;

    @Parameter
    @Summary("Condition name.")
    public String name;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Condition negation.")
    public boolean negation;
    
    @Parameter
    @Summary("Occurrence operator.")
    public OperatorEnumFE operator;
    
    @Parameter
    @Summary("Condition value.")
    public String value;

    public String getFieldName() {
        return fieldName;
    }

    public String getName() {
        return name;
    }

    public boolean isNegation() {
        return negation;
    }

    public OperatorEnumFE getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}


