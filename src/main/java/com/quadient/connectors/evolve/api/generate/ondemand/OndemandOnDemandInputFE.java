package com.quadient.connectors.evolve.api.generate.ondemand;

import com.quadient.connectors.evolve.api.generate.InputVariablesOptionsFE;
import com.quadient.connectors.evolve.api.generate.MultipartAttachmentFE;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class OndemandOnDemandInputFE {
    @Parameter
    @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:'Folder/NestedFolder/pipelineName'.")
    String pipelineName;

    @Parameter
    @Optional
    @NullSafe
    @Summary("List of processing pipeline variables. It can be used to override values of existing variables in the given processing pipeline. E.g. when a variable is used in the pipeline&#39;s output path, by defining a different value for the same codeName, you can easily change the output path as you start the pipeline without having to re-configure the pipeline itself.")
    List<InputVariablesOptionsFE> variables;

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

    public List<InputVariablesOptionsFE> getVariables() {
        return variables;
    }

    public void setVariables(List<InputVariablesOptionsFE> variables) {
        this.variables = variables;
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
