package com.quadient.connectors.evolve.api.contentauthor;

import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

public class MetadataConditionFE {
    @Parameter
    @Summary("Metadata field name.")
    String name;
    
    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Metadata condition negation.")
    boolean negation;

    @Parameter
    @Summary("Occurrence operator.")
    OperatorEnumFE operator;

    @Parameter
    @Summary("Metadata field value.")
    String value;

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
