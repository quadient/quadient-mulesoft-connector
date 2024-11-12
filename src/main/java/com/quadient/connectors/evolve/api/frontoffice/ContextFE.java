package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;

import java.util.List;

public class ContextFE {
    @Parameter
    @DisplayName("Paths")
    public List<String> frontOfficeContextPaths;

    @Parameter
    public String value;

    public List<String> getFrontOfficeContextPaths() {
        return frontOfficeContextPaths;
    }

    public String getValue() {
        return value;
    }
}