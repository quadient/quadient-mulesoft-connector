package org.mule.extension.quadient.internal.operation.frontoffice;

import com.quadient.mule.model.fo.ApprovalProcessPath;
import com.quadient.mule.model.fo.Command;
import com.quadient.mule.model.fo.Context;
import com.quadient.mule.model.fo.Contract;
import com.quadient.mule.model.fo.DataDefinition;
import com.quadient.mule.model.fo.Holder;
import com.quadient.mule.model.fo.Properties;
import org.mule.extension.quadient.api.frontoffice.ApprovalProcessPathFE;
import org.mule.extension.quadient.api.frontoffice.CommandFE;
import org.mule.extension.quadient.api.frontoffice.ContextFE;
import org.mule.extension.quadient.api.frontoffice.ContractFE;
import org.mule.extension.quadient.api.frontoffice.DataDefinitionFE;
import org.mule.extension.quadient.api.frontoffice.HolderTypeFE;
import org.mule.extension.quadient.api.frontoffice.PropertiesFE;
import org.mule.extension.quadient.internal.config.Configuration;
import org.mule.extension.quadient.internal.connection.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.error.provider.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.operation.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operation.ServiceEndpoint;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.sdk.api.annotation.param.Config;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Placement;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.List;

public class FrontOfficeCreateTicketOperation {
    static final String ENDPOINT = ServiceEndpoint.FO_TICKETS;
    static final HttpConstants.Method METHOD = HttpConstants.Method.POST;

    @OutputJsonType(schema = "jsonSchema/fo-frontOfficeCreateTicketOperation.json")
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Creates a ticket.")
    @DisplayName("Front Office - Create ticket")
    public Result<InputStream, HttpResponseAttributes> frontOfficeCreateTicket(
            @Config Configuration configuration,
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,

            @Summary("Initial state of the ticket, the state must be in the approval process.")
            @Example("S_simple_scenario_writer_assigned")
            String stateId,

            @Summary("Define the holder of the ticket.")
            @Example("lee@vital.com")
            String holderValue,

            @Summary("Define the holder of the ticket.")
            @DisplayName("Holder Type")
            HolderTypeFE crateTicketHolderType,

            @Summary("Name of the processing pipeline to be run.")
            @Optional
            boolean addAttachmentFromGlobalStorageEnabled,

            @Summary("Determines whether the ticket will be created asynchronously.")
            @Optional
            boolean asynchronousProcessing,

            @Summary("Determines whether the ticket will be handled as a multipleRecord ticket (for multiple recipients).")
            @Optional
            boolean multipleRecord,

            @Summary("Determines whether a user can add attachments from a local drive.")
            @Optional
            boolean uploadAttachmentFromLocalDriveEnabled,

            @Example("SMS, EMAIL, EMAIL_WITH_ATTACHMENT, PRINT")
            @Summary("Determines which production actions will be available in the ticket.")
            @Optional
            @NullSafe
            List<String> productionActions,

            @Summary("This parameter overrides the default selection of the ticket approval process.")
            @DisplayName("Approval Process Path")
            @Optional
            ApprovalProcessPathFE createTicketApprovalProcessPath,

            @Example("'Gift Voucher - GPK Adventure - Africa.jpg', 'Gift Voucher - Froodeco - Bouquet.pdf'")
            @Summary("Attachments to be added to the ticket.")
            @Optional
            @NullSafe
            List<String> attachments,

            @Summary("Define the contract details.")
            @DisplayName("Contract")
            @Optional
            ContractFE createTicketContract,

            @Summary("Define the required ticket properties.")
            @DisplayName("Properties")
            @Optional
            PropertiesFE createTicketProperties,

            //End of CreateTicketBaseDto

            @Summary("Path to an existing template the ticket will be created from.")
            @Placement(order = 1)
            String templateName,

            @Summary("Define the brand for the document.")
            @Optional
            String brand,

            @Summary("A command related to the way of processing a JLD file.")
            @Optional
            @NullSafe
            List<CommandFE> commands,

            @Summary("Sets document's data context.")
            @DisplayName("Context")
            @NullSafe
            @Optional
            List<ContextFE> createTicketContexts,

            @Summary("Create clone of another document specified in create ticket request (index).")
            @DisplayName("Copy Of")
            @Optional
            Integer createTicketCopyOf,

            @Optional
            @NullSafe
            List<DataDefinitionFE> dataDefinitions,

            @Summary("Define the brand for the document.")
            @Optional
            String description,

            @Summary("If true, the ticket is created as a form document ticket.")
            @Optional
            boolean pageDocument
    ) {
        CreateTicket createTicket = new CreateTicket();
        createTicket.setStateId(stateId);
        createTicket.setHolder(new Holder().holder(holderValue).type(Holder.TypeEnum.fromValue(crateTicketHolderType.getValue())));
        createTicket.setAddAttachmentFromGlobalStorageEnabled(addAttachmentFromGlobalStorageEnabled);
        createTicket.setAsynchronousProcessing(asynchronousProcessing);
        createTicket.setMultipleRecord(multipleRecord);
        createTicket.setUploadAttachmentFromLocalDriveEnabled(uploadAttachmentFromLocalDriveEnabled);
        createTicket.setProductionActions(productionActions);
        if (createTicketApprovalProcessPath != null) {
            createTicket.setApprovalProcessPath(new ApprovalProcessPath().resolveDepartment(createTicketApprovalProcessPath.isResolveDepartment()).value(createTicketApprovalProcessPath.getValue()));
        }
        createTicket.setAttachments(attachments);
        if (createTicketContract != null) {
            createTicket.setContract(new Contract().contractId(createTicketContract.getContractId()).contractName(createTicketContract.getContractName()));
        }
        if (createTicketProperties != null) {
            createTicket.setProperties(new Properties().properties(createTicketProperties.getCreateTicketPropertiesMap()).overrideAlsoNotEmptyProperties(createTicketProperties.isOverrideAlsoNotEmptyProperties()));
        }

        createTicket.getDocumentData().brand(brand);

        if (!commands.isEmpty()) {
            commands.forEach(command -> createTicket.getDocumentData().addCommandsItem(new Command().name(command.name).value(command.value)));

        }
        if (createTicketContexts != null) {
            createTicketContexts.forEach(context -> createTicket.getDocumentData().addContextItem(new Context().path(context.path).value(context.value)));
        }
        if (createTicketCopyOf != null) {
            createTicket.getDocumentData().copyOf(createTicketCopyOf.longValue());
        }

        if (dataDefinitions != null) {
            dataDefinitions.forEach(dataDefinition -> createTicket.getDocumentData().addDataDefinitionsItem(
                    new DataDefinition()
                            .moduleName(dataDefinition.moduleName)
                            .type(DataDefinition.TypeEnum.fromValue(dataDefinition.type.getValue()))
                            .value(dataDefinition.value)));
        }
        createTicket.getDocumentData().description(description);
        createTicket.getDocumentData().pageDocument(pageDocument);
        createTicket.getDocumentData().templateName(templateName);

        return connection.sendRequest(METHOD, ENDPOINT, new ObjectConverter().convertToJson(createTicket), null);
    }
}
