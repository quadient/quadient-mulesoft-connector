package org.mule.extension.quadient.internal.operations.ca.fe;

public enum OperatorEnumFE {
    EQUAL("equal"),

    LESS("less"),

    LESS_OR_EQUAL("lessOrEqual"),

    MORE("more"),

    MORE_OR_EQUAL("moreOrEqual"),

    BEGIN_WITH("beginWith"),

    BEGIN_WITH_CASE_INSENSITIVE("beginWithCaseInsensitive"),

    END_WITH("endWith"),

    END_WITH_CASE_INSENSITIVE("endWithCaseInsensitive"),

    CONTAINS("contains"),

    CONTAINS_CASE_INSENSITIVE("containsCaseInsensitive"),

    IS_VALID("isValid"),

    EXISTS("exists"),

    EMPTY("empty");

    private String value;

    OperatorEnumFE(String value) {
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
