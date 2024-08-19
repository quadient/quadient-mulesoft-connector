package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.runtime.api.util.MultiMap;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;

public class PropertiesFE {
    @Parameter
    @Summary("Override properties with template properties after template selection.")
    public boolean overrideAlsoNotEmptyProperties;
    
    @Parameter
    @Summary("Define custom ticket properties.")
    @DisplayName("Properties")
    public MultiMap<String, String> createTicketPropertiesMap;

    public boolean isOverrideAlsoNotEmptyProperties() {
        return overrideAlsoNotEmptyProperties;
    }

    public void setOverrideAlsoNotEmptyProperties(boolean overrideAlsoNotEmptyProperties) {
        this.overrideAlsoNotEmptyProperties = overrideAlsoNotEmptyProperties;
    }

    public MultiMap<String, String> getCreateTicketPropertiesMap() {
        return createTicketPropertiesMap;
    }

    public void setCreateTicketPropertiesMap(MultiMap<String, String> createTicketPropertiesMap) {
        this.createTicketPropertiesMap = createTicketPropertiesMap;
    }
}
