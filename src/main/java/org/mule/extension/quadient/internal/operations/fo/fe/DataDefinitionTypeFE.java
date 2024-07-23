package org.mule.extension.quadient.internal.operations.fo.fe;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DataDefinitionTypeFE {

    ICM_LOCATION("icmLocation"),

    BINARY_BASE64_DATA("binaryBase64Data"),

    XML("xml"),

    JSON("json");

    private String value;

    DataDefinitionTypeFE(String value) {
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
