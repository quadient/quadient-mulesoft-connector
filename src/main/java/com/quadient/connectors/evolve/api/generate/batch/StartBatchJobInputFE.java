package com.quadient.connectors.evolve.api.generate.batch;

import com.quadient.connectors.evolve.api.generate.InputVariablesOptionsFE;
import com.quadient.connectors.evolve.api.generate.MultipartAttachmentFE;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class StartBatchJobInputFE {
    @Parameter
    @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:'Folder/NestedFolder/pipelineName'.")
    String pipelineName;

    @Parameter
    @Summary("Unique identifier of an existing job working folder. If a different working folder is set in the pipeline, the folder specified here takes priority.")
    String workingFolderId;

    @Parameter
    @Summary("Custom description of the job.")
    String description;

    @Parameter
    @Optional
    @NullSafe
    @Summary("List of processing pipeline variables. It can be used to override values of existing variables in the given processing pipeline. E.g. when a variable is used in the pipeline&#39;s output path, by defining a different value for the same codeName, you can easily change the output path as you start the pipeline without having to re-configure the pipeline itself.")
    List<InputVariablesOptionsFE> variables;

    @Parameter
    @Optional
    @Summary("Specifies the job priority. Jobs with higher priority are run first. The  value set here overwrites any priority set when designing the pipeline.\n" +
            "   * minimum: 1\n" +
            "   * maximum: 100")
    Long priority;

    @Parameter
    @Optional
    @Summary("Date and time  the batch expires (date/time format according to the ISO 8601 standard). Once expired, the job is deleted. If undefined, the job will expire in 90 days.")
    @Example("2024-07-10T10:15:30+01:00")
    String expiration;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("If true, the job will be run using a draft version of the processing pipeline.")
    boolean useDraftPipeline;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("If true, the job will be run using a draft version of the relevant resources (scripts, connectors).")
    boolean useDraftResources;

    @Parameter
    @Optional
    @NullSafe
    List<MultipartAttachmentFE> attachments;

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getWorkingFolderId() {
        return workingFolderId;
    }

    public void setWorkingFolderId(String workingFolderId) {
        this.workingFolderId = workingFolderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InputVariablesOptionsFE> getVariables() {
        return variables;
    }

    public void setVariables(List<InputVariablesOptionsFE> variables) {
        this.variables = variables;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public boolean isUseDraftPipeline() {
        return useDraftPipeline;
    }

    public void setUseDraftPipeline(boolean useDraftPipeline) {
        this.useDraftPipeline = useDraftPipeline;
    }

    public boolean isUseDraftResources() {
        return useDraftResources;
    }

    public void setUseDraftResources(boolean useDraftResources) {
        this.useDraftResources = useDraftResources;
    }

    public List<MultipartAttachmentFE> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MultipartAttachmentFE> attachments) {
        this.attachments = attachments;
    }
}
