package org.mule.extension.quadient.internal.operation.generate_v6.batch;

import com.quadient.mule.model.v6.batch.CreateWorkingFolderRequest;
import com.quadient.mule.model.v6.batch.CreateWorkingFolderResponse;
import org.mule.extension.quadient.internal.config.Configuration;
import org.mule.extension.quadient.internal.connection.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.error.provider.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.error.exception.InvalidInputParameterException;
import org.mule.extension.quadient.internal.operation.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operation.ServiceEndpoint;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.Config;
import org.mule.sdk.api.annotation.param.MediaType;
import org.mule.sdk.api.annotation.param.Optional;
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
    @DisplayName("Batch - Create working folder")
    public Result<String, HttpResponseAttributes> batchCreateWorkingFolder(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Summary("Name of the job working folder. It will be visible in GUI and will be a part of the folder's ID.")
            String name,

            @Optional
            @Summary("Date and time the folder will expire and be deleted (date/time format according to the ISO 8601 standard).\n The behavior of the parameter changes based on the isJobDedicated settings, i.e. if isJobDedicated is true or undefined, expiration cannot be set to more than 90 days and leaving expiration undefined sets the expiration to 90 days; if isJobDedicated is false, expiration can be set to an arbitrary date and leaving expiration undefined causes the folder to never expire.")
            String expiration,

            @Optional(defaultValue = "true")
            @Summary("If true, the working folder will behave as an automatically created working folder while allowing you to prepare input data in advance.")
            boolean isJobDedicated
    ) {
        if (name.isEmpty() || name.length() > 20) {
            throw new InvalidInputParameterException(new Exception("Name must be between 1 and 20 characters."));
        }
        ObjectConverter objectConverter = new ObjectConverter();
        CreateWorkingFolderRequest request = new CreateWorkingFolderRequest().name(name).expiration(expiration).isJobDedicated(isJobDedicated);
        Result<InputStream, HttpResponseAttributes> result = connection.sendPOSTRequest(ENDPOINT, objectConverter.convertToJson(request));
        CreateWorkingFolderResponse responseObj = objectConverter.readValue(result.getOutput(), CreateWorkingFolderResponse.class);

        return Result.<String, HttpResponseAttributes>builder()
                .output(responseObj.getWorkingFolderId())
                .attributes(result.getAttributes().orElse(null))
                .mediaType(org.mule.runtime.api.metadata.MediaType.TEXT)
                .build();  
    }
}
