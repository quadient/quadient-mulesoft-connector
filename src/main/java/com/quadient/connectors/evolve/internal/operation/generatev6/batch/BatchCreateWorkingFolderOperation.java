package com.quadient.connectors.evolve.internal.operation.generatev6.batch;

import com.quadient.connectors.evolve.api.generate.batch.BatchCreateWorkingFolderInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.error.provider.ExecuteErrorsProvider;
import com.quadient.connectors.evolve.internal.error.exception.InvalidInputParameterException;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.generated.model.v6.batch.CreateWorkingFolderRequest;
import com.quadient.connectors.generated.model.v6.batch.CreateWorkingFolderResponse;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.MediaType;
import org.mule.sdk.api.annotation.param.ParameterGroup;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;


public class BatchCreateWorkingFolderOperation {
    static final String ENDPOINT = ServiceEndpoint.BATCH_CREATE_WORKING_FOLDER;

    @MediaType(MediaType.TEXT_PLAIN)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Creates a working folder for your batch job and returns working folder id.\n" +
            "Output contains working folder id.")
    @DisplayName("Batch - Create Working Folder")
    public Result<String, HttpResponseAttributes> batchCreateWorkingFolder(
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,
            @ParameterGroup(name = "Create Working Folder") BatchCreateWorkingFolderInputFE input
    ) {
        if (input.getName().isEmpty() || input.getName().length() > 20) {
            throw new InvalidInputParameterException(new Exception("Name must be between 1 and 20 characters."));
        }
        ObjectConverter objectConverter = new ObjectConverter();
        CreateWorkingFolderRequest request = new CreateWorkingFolderRequest().name(input.getName()).expiration(input.getExpiration()).isJobDedicated(input.isJobDedicated());
        Result<InputStream, HttpResponseAttributes> result = connection.sendPOSTRequest(ENDPOINT, objectConverter.convertToJson(request));
        CreateWorkingFolderResponse responseObj = objectConverter.readValue(result.getOutput(), CreateWorkingFolderResponse.class);

        return Result.<String, HttpResponseAttributes>builder()
                .output(responseObj.getWorkingFolderId())
                .attributes(result.getAttributes().orElse(null))
                .mediaType(org.mule.runtime.api.metadata.MediaType.TEXT)
                .build();
    }
}
