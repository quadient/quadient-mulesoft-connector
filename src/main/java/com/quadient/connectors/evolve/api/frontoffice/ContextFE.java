package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.Parameter;

import java.util.List;

public class ContextFE {
    @Parameter
    public List<String> paths;

    @Parameter
    public String value;

    public List<String> getPaths() {
        return paths;
    }

    public String getValue() {
        return value;
    }
}