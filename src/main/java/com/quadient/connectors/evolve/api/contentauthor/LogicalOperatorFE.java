package com.quadient.connectors.evolve.api.contentauthor;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LogicalOperatorFE {
    AND("and"),

    OR("or");

    private String value;

    LogicalOperatorFE(String value) {
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
