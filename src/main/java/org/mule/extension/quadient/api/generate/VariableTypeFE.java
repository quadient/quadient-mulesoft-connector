package org.mule.extension.quadient.api.generate;

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

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
