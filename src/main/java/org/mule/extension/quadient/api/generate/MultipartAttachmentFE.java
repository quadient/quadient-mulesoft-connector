package org.mule.extension.quadient.api.generate;

import org.mule.runtime.api.metadata.TypedValue;
import org.mule.sdk.api.annotation.param.Content;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.io.InputStream;

public class MultipartAttachmentFE {

    @Parameter
    @Summary("Name of the attachment")
    String name;

    @Parameter
    @Content
    @DisplayName("Data")
    TypedValue<InputStream> multipartData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypedValue<InputStream> getMultipartData() {
        return multipartData;
    }

    public void setMultipartData(TypedValue<InputStream> multipartData) {
        this.multipartData = multipartData;
    }
}
