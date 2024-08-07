package org.mule.extension.quadient.internal.config;

import org.mule.extension.quadient.internal.connection.provider.ConnectionProvider;
import org.mule.extension.quadient.internal.operation.generateV6.batch.BatchJobStatusOperation;
import org.mule.extension.quadient.internal.operation.generateV6.batch.BatchCreateWorkingFolderOperation;
import org.mule.extension.quadient.internal.operation.generateV6.batch.StartBatchJobOperation;
import org.mule.extension.quadient.internal.operation.contentAuthor.ContentAuthorTemplatesOperation;
import org.mule.extension.quadient.internal.operation.frontOffice.FrontOfficeCreateTicketOperation;
import org.mule.extension.quadient.internal.operation.generateV6.ondemand.OnDemandCustomDataOperation;
import org.mule.extension.quadient.internal.operation.generateV6.ondemand.OnDemandOnDemandOperation;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.RefName;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations({
        StartBatchJobOperation.class,
        OnDemandOnDemandOperation.class,
        BatchJobStatusOperation.class,
        BatchCreateWorkingFolderOperation.class,
        OnDemandCustomDataOperation.class,
        ContentAuthorTemplatesOperation.class,
        FrontOfficeCreateTicketOperation.class
})
@ConnectionProviders(ConnectionProvider.class)
public class Configuration {

    @RefName
    private String configName;

    public String getConfigName() {
        return configName;
    }
}
