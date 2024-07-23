package org.mule.extension.quadient.internal.operations.fo;

public enum HolderType {
    USER_NAME("userName"),
    USER_GROUP("userGroup");
    private final String value;

    HolderType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
