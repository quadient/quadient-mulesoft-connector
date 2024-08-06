package org.mule.extension.quadient.internal.operations.ca.fe;

public enum LogicalOperatorFE {
    AND("and"),

    OR("or");

    private String value;

    LogicalOperatorFE(String value) {
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
