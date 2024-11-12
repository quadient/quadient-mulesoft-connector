package com.quadient.connectors.evolve.api.generate.ondemand;

import com.quadient.connectors.evolve.api.generate.InputVariablesOptionsFE;
import com.quadient.connectors.evolve.api.generate.MultipartAttachmentFE;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class OnDemandOnDemandInputFE {
    @Parameter
    @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:'Folder/NestedFolder/pipelineName'.")
    public String pipelineName;

    @Parameter
    @Optional
    @DisplayName("Variables")
    @NullSafe
    @Summary("List of processing pipeline variables. It can be used to override values of existing variables in the given processing pipeline. E.g. when a variable is used in the pipeline&#39;s output path, by defining a different value for the same codeName, you can easily change the output path as you start the pipeline without having to re-configure the pipeline itself.")
    public List<InputVariablesOptionsFE> onDemandOnDemandVariables;

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
    @DisplayName("Attachments")
    @NullSafe
    public List<MultipartAttachmentFE> onDemandOnDemandAttachments;

    public String getPipelineName() {
        return pipelineName;
    }

    public List<InputVariablesOptionsFE> getOnDemandOnDemandVariables() {
        return onDemandOnDemandVariables;
    }

    public boolean isUseDraftPipeline() {
        return useDraftPipeline;
    }

    public boolean isUseDraftResources() {
        return useDraftResources;
    }

    public List<MultipartAttachmentFE> getOnDemandOnDemandAttachments() {
        return onDemandOnDemandAttachments;
    }
}
