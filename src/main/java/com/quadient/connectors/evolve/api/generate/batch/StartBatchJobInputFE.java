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
    public String pipelineName;

    @Parameter
    @Optional
    @Summary("Unique identifier of an existing job working folder. If a different working folder is set in the pipeline, the folder specified here takes priority.")
    public String workingFolderId;

    @Parameter
    @Optional
    @Summary("Custom description of the job.")
    public String description;

    @Parameter
    @Optional
    @NullSafe
    @Summary("List of processing pipeline variables. It can be used to override values of existing variables in the given processing pipeline. E.g. when a variable is used in the pipeline&#39;s output path, by defining a different value for the same codeName, you can easily change the output path as you start the pipeline without having to re-configure the pipeline itself.")
    public List<InputVariablesOptionsFE> variables;

    @Parameter
    @Optional
    @Summary("Specifies the job priority. Jobs with higher priority are run first. The  value set here overwrites any priority set when designing the pipeline.\n" +
            "   * minimum: 1\n" +
            "   * maximum: 100")
    public Long priority;

    @Parameter
    @Optional
    @Summary("Date and time  the batch expires (date/time format according to the ISO 8601 standard). Once expired, the job is deleted. If undefined, the job will expire in 90 days.")
    @Example("2024-07-10T10:15:30+01:00")
    public String expiration;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("If true, the job will be run using a draft version of the processing pipeline.")
    public boolean useDraftPipeline;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("If true, the job will be run using a draft version of the relevant resources (scripts, connectors).")
    public boolean useDraftResources;

    @Parameter
    @Optional
    @NullSafe
    public List<MultipartAttachmentFE> attachments;

    public String getPipelineName() {
        return pipelineName;
    }

    public String getWorkingFolderId() {
        return workingFolderId;
    }

    public String getDescription() {
        return description;
    }

    public List<InputVariablesOptionsFE> getVariables() {
        return variables;
    }

    public Long getPriority() {
        return priority;
    }

    public String getExpiration() {
        return expiration;
    }

    public boolean isUseDraftPipeline() {
        return useDraftPipeline;
    }

    public boolean isUseDraftResources() {
        return useDraftResources;
    }

    public List<MultipartAttachmentFE> getAttachments() {
        return attachments;
    }
}
