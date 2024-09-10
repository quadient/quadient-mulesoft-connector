package com.quadient.connectors.evolve.api.generate;

import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;

import java.util.List;

public class InputVariablesOptionsFE {

    @Parameter
    public VariableTypeFE type;

    @Parameter
    public String codeName;

    @Parameter
    public String value;

    @Parameter
    @Optional
    @NullSafe
    public List<String> options;

    public VariableTypeFE getType() {
        return type;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getValue() {
        return value;
    }

    public List<String> getOptions() {
        return options;
    }
}