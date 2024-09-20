package com.quadient.connectors.evolve.api.contentauthor;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperatorEnumFE {
    EQUAL("equal"),

    LESS("less"),

    LESSOREQUAL("lessOrEqual"),

    MORE("more"),

    MOREOREQUAL("moreOrEqual"),

    BEGINWITH("beginWith"),

    BEGINWITHCASEINSENSITIVE("beginWithCaseInsensitive"),

    ENDWITH("endWith"),

    ENDWITHCASEINSENSITIVE("endWithCaseInsensitive"),

    CONTAINS("contains"),

    CONTAINSCASEINSENSITIVE("containsCaseInsensitive"),

    ISVALID("isValid"),

    EMPTY("empty");

    private String value;

    OperatorEnumFE(String value) {
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
