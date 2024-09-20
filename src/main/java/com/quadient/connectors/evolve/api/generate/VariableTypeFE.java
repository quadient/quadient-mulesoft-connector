package com.quadient.connectors.evolve.api.generate;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VariableTypeFE {

    PIPELINE("Pipeline"),

    GLOBAL("Global"),

    SYSTEM("System"),

    CUSTOM("Custom"),

    STEP("Step");

    private final String value;

    VariableTypeFE(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
