package com.quadient.connectors.evolve.api.frontoffice;

public enum HolderTypeFE {
    USER_NAME("userName"),
    USER_GROUP("userGroup");
    private final String value;

    HolderTypeFE(String value) {
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
