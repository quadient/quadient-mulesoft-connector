package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

public class CommandFE {
    @Parameter
    @Summary("Command name.")
    public String name;
    
    @Parameter
    @Summary("Command value.")
    public String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
