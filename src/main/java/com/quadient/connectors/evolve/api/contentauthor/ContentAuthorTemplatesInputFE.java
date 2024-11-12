package com.quadient.connectors.evolve.api.contentauthor;

import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

public class ContentAuthorTemplatesInputFE {
    @Parameter
    @Optional
    @Summary("Name of folder whose content will be listed.")
    public String folder;

    @Parameter
    @Optional
    @Summary("Number of items to skip before starting to collect the resulting.")
    public int offset;

    @Parameter
    @Optional(defaultValue = "20")
    @Summary("Number of items to return (max. 100).")
    public int limit;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Determines whether to include metadata in the response.")
    public boolean includeMetadata;

    @Parameter
    @Optional
    @Summary("List templates that the specified user can see.")
    public String holder;

    @Parameter
    @Optional
    @Example("Production:Testing")
    @Summary("List templates that have the specified approval states, separated by a colon.")
    public String approvalStates;

    @Parameter
    @Optional
    @Summary("Conditions can be nested and can contain the same elements as the main condition.")
    @DisplayName("Condition")
    public ConditionFE contentAuthorTemplatesCondition;

    public String getFolder() {
        return folder;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isIncludeMetadata() {
        return includeMetadata;
    }

    public String getHolder() {
        return holder;
    }

    public String getApprovalStates() {
        return approvalStates;
    }

    public ConditionFE getContentAuthorTemplatesCondition() {
        return contentAuthorTemplatesCondition;
    }
}
