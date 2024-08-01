package org.mule.extension.quadient.internal.operations.v6.fe;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;

public class InputVariablesOptionsFE {

    @Parameter
    public VariableTypeFE type;

    @Parameter
    public String codeName;

    @Parameter
    public String value;

    @Optional
    @Parameter
    public String[] options;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public VariableTypeFE getType() {
        return type;
    }

    public void setType(VariableTypeFE type) {
        this.type = type;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}