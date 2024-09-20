package com.quadient.connectors.evolve.api.generate;

import org.mule.runtime.api.metadata.TypedValue;
import org.mule.sdk.api.annotation.param.Content;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.io.InputStream;

public class MultipartAttachmentFE {

    @Parameter
    @Summary("Name of the attachment")
    public String name;

    @Parameter
    @Content
    @DisplayName("Data")
    public TypedValue<InputStream> multipartData;

    public String getName() {
        return name;
    }

    public TypedValue<InputStream> getMultipartData() {
        return multipartData;
    }
}
