package com.quadient.connectors.evolve.api.frontoffice;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HolderTypeFE {
    USER_NAME("userName"),
    USER_GROUP("userGroup");
    private final String value;

    HolderTypeFE(String value) {
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
