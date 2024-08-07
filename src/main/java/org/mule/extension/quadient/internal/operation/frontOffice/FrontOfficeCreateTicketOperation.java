package org.mule.extension.quadient.internal.operation.frontOffice;

import com.quadient.mule.model.fo.*;
import org.mule.extension.quadient.api.frontOffice.*;
import org.mule.extension.quadient.internal.config.Configuration;
import org.mule.extension.quadient.internal.connection.Connection;
import org.mule.extension.quadient.internal.ObjectConverter;
import org.mule.extension.quadient.internal.error.provider.ExecuteErrorsProvider;
import org.mule.extension.quadient.internal.operation.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operation.ServiceEndpoint;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Placement;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;
import java.util.List;

public class FrontOfficeCreateTicketOperation {
    final String endpoint = ServiceEndpoint.FO_TICKETS;
    final HttpConstants.Method method = HttpConstants.Method.POST;

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
        createTicket.stateId = stateId;
        createTicket.holder = new Holder().holder(holderValue).type(Holder.TypeEnum.fromValue(crateTicketHolderType.getValue()));
        createTicket.addAttachmentFromGlobalStorageEnabled = addAttachmentFromGlobalStorageEnabled;
        createTicket.asynchronousProcessing = asynchronousProcessing;
        createTicket.multipleRecord = multipleRecord;
        createTicket.uploadAttachmentFromLocalDriveEnabled = uploadAttachmentFromLocalDriveEnabled;
        createTicket.productionActions = productionActions;
        if (createTicketApprovalProcessPath != null) {
            createTicket.approvalProcessPath = new ApprovalProcessPath().resolveDepartment(createTicketApprovalProcessPath.isResolveDepartment()).value(createTicketApprovalProcessPath.getValue());
        }
        createTicket.attachments = attachments;
        if (createTicketContract != null) {
            createTicket.contract = new Contract().contractId(createTicketContract.getContractId()).contractName(createTicketContract.getContractName());
        }
        if (createTicketProperties != null) {
            createTicket.properties = new Properties().properties(createTicketProperties.getCreateTicketPropertiesMap()).overrideAlsoNotEmptyProperties(createTicketProperties.isOverrideAlsoNotEmptyProperties());
        }

        createTicket.documentData.brand(brand);

        if (!commands.isEmpty()) {
            commands.forEach(command -> {
                createTicket.documentData.addCommandsItem(new Command().name(command.name).value(command.value));
            });

        }
        if (createTicketContexts != null) {
            createTicketContexts.forEach(context -> {
                createTicket.documentData.addContextItem(new Context().path(context.path).value(context.value));
            });
        }
        if (createTicketCopyOf != null) {
            createTicket.documentData.copyOf(createTicketCopyOf.longValue());
        }

        if (dataDefinitions != null) {
            dataDefinitions.forEach(dataDefinition -> {
                createTicket.documentData.addDataDefinitionsItem(
                        new DataDefinition()
                                .moduleName(dataDefinition.moduleName)
                                .type(DataDefinition.TypeEnum.fromValue(dataDefinition.type.getValue()))
                                .value(dataDefinition.value));
            });
        }
        createTicket.documentData.description(description);
        createTicket.documentData.pageDocument(pageDocument);
        createTicket.documentData.templateName(templateName);

        return connection.sendRequest(method, endpoint, new ObjectConverter().convertToJson(createTicket), null);
    }
}
