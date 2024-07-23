package org.mule.extension.quadient.internal.operations.fo.fe;

import org.mule.sdk.api.annotation.param.Parameter;

import java.util.List;

public class ContextFE {
    @Parameter
    public List<String> path;

    @Parameter
    public String value;

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}