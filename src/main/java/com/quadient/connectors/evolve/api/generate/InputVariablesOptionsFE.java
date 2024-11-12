package com.quadient.connectors.evolve.api.generate;

import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;

import java.util.List;

public class InputVariablesOptionsFE {

    @Parameter
    @DisplayName("Type")
    public VariableTypeFE inputVariablesType;

    @Parameter
    public String codeName;

    @Parameter
    public String value;

    @Parameter
    @Optional
    @DisplayName("Options")
    @NullSafe
    public List<String> inputVariablesOptions;

    public VariableTypeFE getInputVariablesType() {
        return inputVariablesType;
    }

    public String getCodeName() {
        return codeName;
    }

    public String getValue() {
        return value;
    }

    public List<String> getInputVariablesOptions() {
        return inputVariablesOptions;
    }
}