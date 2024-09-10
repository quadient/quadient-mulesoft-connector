package com.quadient.connectors.evolve.api.generate.batch;

import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

public class BatchCreateWorkingFolderInputFE {
    @Parameter
    @Summary("Name of the job working folder. It will be visible in GUI and will be a part of the folder's ID.")
    String name;

    @Parameter
    @Optional
    @Example("2024-12-31T23:59:59Z")
    @Summary("Date and time the folder will expire and be deleted (date/time format according to the ISO 8601 standard).\n The behavior of the parameter changes based on the isJobDedicated settings, i.e. if isJobDedicated is true or undefined, expiration cannot be set to more than 90 days and leaving expiration undefined sets the expiration to 90 days; if isJobDedicated is false, expiration can be set to an arbitrary date and leaving expiration undefined causes the folder to never expire.")
    String expiration;

    @Parameter
    @Optional(defaultValue = "true")
    @Summary("If true, the working folder will behave as an automatically created working folder while allowing you to prepare input data in advance.")
    boolean isJobDedicated;

    public String getName() {
        return name;
    }

    public String getExpiration() {
        return expiration;
    }

    public boolean isJobDedicated() {
        return isJobDedicated;
    }
}
