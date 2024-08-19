package com.quadient.connectors.evolve.api.frontoffice;

public enum DataDefinitionTypeFE {

    ICM_LOCATION("icmLocation"),

    BINARY_BASE64_DATA("binaryBase64Data"),

    XML("xml"),

    JSON("json");

    private String value;

    DataDefinitionTypeFE(String value) {
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
