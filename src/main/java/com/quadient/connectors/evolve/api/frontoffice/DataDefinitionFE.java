package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

public class DataDefinitionFE {

    @Parameter
    @Summary("Define the name of the module into which data will be saved.")
    public String moduleName;

    @Parameter
    @Summary("Define the data input type.")
    public DataDefinitionTypeFE type;

    @Parameter
    @Summary("You can define binary data (binaryBase64Data), enter the source ICM location, or define XML/JSON directly.")
    public String value;

    public String getModuleName() {
        return moduleName;
    }

    public DataDefinitionTypeFE getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}


