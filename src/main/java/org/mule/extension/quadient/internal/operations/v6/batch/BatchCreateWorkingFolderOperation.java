package org.mule.extension.quadient.internal.operations.v6.batch;

import com.quadient.mule.model.v6.batch.CreateWorkingFolderRequest;
import com.quadient.mule.model.v6.batch.CreateWorkingFolderResponse;
import org.mule.extension.quadient.internal.Configuration;
import org.mule.extension.quadient.internal.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.errors.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.errors.exception.InvalidInputParameterException;
import org.mule.extension.quadient.internal.operations.ServiceEndpoint;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.param.display.DisplayName;

import java.io.InputStream;


public class BatchCreateWorkingFolderOperation {
    final String endpoint = ServiceEndpoint.BATCH_CREATE_WORKING_FOLDER;

    @MediaType(MediaType.TEXT_PLAIN)
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Creates a working folder for your batch job and returns working folder id.\n" +
            "Output contains working folder id.")
    @DisplayName("Batch - Create working folder")
    public String batchCreateWorkingFolder(
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
        InputStream content = connection.sendPOSTRequest(endpoint, objectConverter.convertToJson(request));
        CreateWorkingFolderResponse responseObj = objectConverter.readValue(content, CreateWorkingFolderResponse.class);
        return responseObj.getWorkingFolderId();
    }
}
