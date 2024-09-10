package com.quadient.connectors.evolve.api.generate.ondemand;

import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.util.MultiMap;
import org.mule.sdk.api.annotation.param.Content;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;

public class OnDemandCustomDataInputFE {
    @Parameter
    @Summary("Unique name of the processing pipeline. If the pipeline is inside a folder, this parameter must contain the whole path, e.g. PipelineName:\"Folder/NestedFolder/pipelineName\".")
    String pipelineName;

    @Parameter
    @Optional
    @Summary("Gives a custom name to the data file.")
    String fileName;

    @Parameter
    @Optional
    @Summary("Data file is saved to the specified folder in the working folder | input directory.")
    String folder;

    @Parameter
    @Optional
    @NullSafe
    @DisplayName("Variables")
    @Summary("List of pipeline variables used for the job. The maximum number of these variables is 50.")
    MultiMap<String, String> pipelineVariables;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("If true, the job will be run using a draft version of the processing pipeline.")
    boolean useDraftPipeline;

    @Parameter
    @Optional(defaultValue = "false")
    @Summary("If true, the job will be run using a draft version of the relevant resources (scripts, connectors).")
    boolean useDraftResources;

    @Parameter
    @Content(primary = true)
    TypedValue<Object> customData;

    public String getPipelineName() {
        return pipelineName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFolder() {
        return folder;
    }

    public MultiMap<String, String> getPipelineVariables() {
        return pipelineVariables;
    }

    public boolean isUseDraftPipeline() {
        return useDraftPipeline;
    }

    public boolean isUseDraftResources() {
        return useDraftResources;
    }

    public TypedValue<Object> getCustomData() {
        return customData;
    }
}
