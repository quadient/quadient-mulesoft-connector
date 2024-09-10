package com.quadient.connectors.evolve.api.contentauthor;

import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

public class ContentAuthorTemplatesInputFE {
    @Parameter
    @Optional
    @Summary("Name of folder whose content will be listed.")
    String folder;

    @Parameter
    @Optional
    @Summary("Number of items to skip before starting to collect the resulting.")
    int offset;

    @Parameter
    @Optional(defaultValue = "20")
    @Summary("Number of items to return (max. 100).")
    int limit;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("Determines whether to include metadata in the response.")
    boolean includeMetadata;

    @Parameter
    @Optional
    @Summary("List templates that the specified user can see.")
    String holder;

    @Parameter
    @Optional
    @Example("Production:Testing")
    @Summary("List templates that have the specified approval states, separated by a colon.")
    String approvalStates;

    @Parameter
    @Optional
    @Summary("Conditions can be nested and can contain the same elements as the main condition.")
    ConditionFE condition;

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isIncludeMetadata() {
        return includeMetadata;
    }

    public void setIncludeMetadata(boolean includeMetadata) {
        this.includeMetadata = includeMetadata;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getApprovalStates() {
        return approvalStates;
    }

    public void setApprovalStates(String approvalStates) {
        this.approvalStates = approvalStates;
    }

    public ConditionFE getCondition() {
        return condition;
    }

    public void setCondition(ConditionFE condition) {
        this.condition = condition;
    }
}
