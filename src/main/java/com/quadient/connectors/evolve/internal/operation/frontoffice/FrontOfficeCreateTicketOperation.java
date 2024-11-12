package com.quadient.connectors.evolve.internal.operation.frontoffice;

import com.quadient.connectors.evolve.api.frontoffice.FrontOfficeCreateTicketInputFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.error.provider.ExecuteErrorsProvider;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import com.quadient.connectors.evolve.internal.operation.ServiceEndpoint;
import com.quadient.connectors.generated.model.fo.ApprovalProcessPath;
import com.quadient.connectors.generated.model.fo.Command;
import com.quadient.connectors.generated.model.fo.Context;
import com.quadient.connectors.generated.model.fo.Contract;
import com.quadient.connectors.generated.model.fo.DataDefinition;
import com.quadient.connectors.generated.model.fo.Holder;
import com.quadient.connectors.generated.model.fo.Properties;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.sdk.api.annotation.error.Throws;
import org.mule.sdk.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.sdk.api.annotation.param.ParameterGroup;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Summary;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.InputStream;

public class FrontOfficeCreateTicketOperation {
    static final String ENDPOINT = ServiceEndpoint.FO_TICKETS;
    static final HttpConstants.Method METHOD = HttpConstants.Method.POST;

    @OutputJsonType(schema = "jsonSchema/fo-frontOfficeCreateTicketOperation.json")
    @Throws(ExecuteErrorsProvider.class)
    @Summary("Creates a ticket.")
    @DisplayName("Front Office - Create ticket")
    public Result<InputStream, HttpResponseAttributes> frontOfficeCreateTicket(
            @org.mule.runtime.extension.api.annotation.param.Connection Connection connection,
            @ParameterGroup(name = "Create ticket") FrontOfficeCreateTicketInputFE input
    ) {
        CreateTicket createTicket = new CreateTicket();
        createTicket.setStateId(input.getStateId());
        createTicket.setHolder(new Holder().holder(input.getHolderValue()).type(Holder.TypeEnum.fromValue(input.getCreateTicketHolderType().getValue())));
        createTicket.setAddAttachmentFromGlobalStorageEnabled(input.isAddAttachmentFromGlobalStorageEnabled());
        createTicket.setAsynchronousProcessing(input.isAsynchronousProcessing());
        createTicket.setMultipleRecord(input.isMultipleRecord());
        createTicket.setUploadAttachmentFromLocalDriveEnabled(input.isUploadAttachmentFromLocalDriveEnabled());
        createTicket.setProductionActions(input.getProductionActions());
        if (input.getCreateTicketApprovalProcessPath() != null) {
            createTicket.setApprovalProcessPath(new ApprovalProcessPath().resolveDepartment(input.getCreateTicketApprovalProcessPath().isResolveDepartment()).value(input.getCreateTicketApprovalProcessPath().getValue()));
        }
        createTicket.setAttachments(input.getCreateTicketAttachments());
        if (input.getCreateTicketContract() != null) {
            createTicket.setContract(new Contract().contractId(input.getCreateTicketContract().getContractId()).contractName(input.getCreateTicketContract().getContractName()));
        }
        if (input.getCreateTicketProperties() != null) {
            createTicket.setProperties(new Properties().properties(input.getCreateTicketProperties().getCreateTicketPropertiesMap()).overrideAlsoNotEmptyProperties(input.getCreateTicketProperties().isOverrideAlsoNotEmptyProperties()));
        }

        createTicket.getDocumentData().brand(input.getBrand());

        if (!input.getCommands().isEmpty()) {
            input.getCommands().forEach(command -> createTicket.getDocumentData().addCommandsItem(new Command().name(command.name).value(command.value)));

        }
        if (input.getCreateTicketContexts() != null) {
            input.getCreateTicketContexts().forEach(context -> createTicket.getDocumentData().addContextItem(new Context().path(context.paths).value(context.value)));
        }
        if (input.getCreateTicketCopyOf() != null) {
            createTicket.getDocumentData().copyOf(input.getCreateTicketCopyOf().longValue());
        }

        if (input.getDataDefinitions() != null) {
            input.getDataDefinitions().forEach(dataDefinition -> createTicket.getDocumentData().addDataDefinitionsItem(
                    new DataDefinition()
                            .moduleName(dataDefinition.moduleName)
                            .type(DataDefinition.TypeEnum.fromValue(dataDefinition.type.getValue()))
                            .value(dataDefinition.value)));
        }
        createTicket.getDocumentData().description(input.getDescription());
        createTicket.getDocumentData().pageDocument(input.isPageDocument());
        createTicket.getDocumentData().templateName(input.getTemplateName());

        return connection.sendRequest(METHOD, ENDPOINT, new ObjectConverter().convertToJson(createTicket), null);
    }
}
