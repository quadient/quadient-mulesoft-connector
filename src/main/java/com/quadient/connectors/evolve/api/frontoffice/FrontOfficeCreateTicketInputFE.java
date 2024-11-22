package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.NullSafe;
import org.mule.sdk.api.annotation.param.Optional;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.DisplayName;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Placement;
import org.mule.sdk.api.annotation.param.display.Summary;

import java.util.List;

public class FrontOfficeCreateTicketInputFE {
    @Parameter
    @DisplayName("State ID")
    @Summary("Initial state of the ticket, the state must be in the approval process.")
    @Example("S_simple_scenario_writer_assigned")
    public String stateId;

    @Parameter
    @Summary("Define the holder of the ticket.")
    @DisplayName("Holder")
    @Example("lee@vital.com")
    public String holderValue;

    @Parameter
    @Summary("Define the holder of the ticket.")
    @DisplayName("Holder type")
    public HolderTypeFE frontOfficeCreateTicketHolderType;

    @Parameter
    @Summary("Name of the processing pipeline to be run.")
    @Optional
    public boolean addAttachmentFromGlobalStorageEnabled;

    @Parameter
    @Summary("Determines whether the ticket will be created asynchronously.")
    @Optional
    public boolean asynchronousProcessing;

    @Parameter
    @Summary("Determines whether the ticket will be handled as a multipleRecord ticket (for multiple recipients).")
    @Optional
    public boolean multipleRecord;

    @Parameter
    @Summary("Determines whether a user can add attachments from a local drive.")
    @Optional
    public boolean uploadAttachmentFromLocalDriveEnabled;

    @Parameter
    @Example("SMS, EMAIL, EMAIL_WITH_ATTACHMENT, PRINT")
    @Summary("Determines which production actions will be available in the ticket.")
    @DisplayName("Production Actions")
    @Optional
    @NullSafe
    public List<String> frontOfficeCreateTicketProductionActions;

    @Parameter
    @Summary("This parameter overrides the default selection of the ticket approval process.")
    @DisplayName("Approval Process Path")
    @Optional
    public ApprovalProcessPathFE frontOfficeCreateTicketApprovalProcessPath;

    @Parameter
    @Example("'Gift Voucher - GPK Adventure - Africa.jpg', 'Gift Voucher - Froodeco - Bouquet.pdf'")
    @Summary("Attachments to be added to the ticket.")
    @DisplayName("Attachments")
    @Optional
    @NullSafe
    public List<String> frontOfficeCreateTicketAttachments;

    @Parameter
    @Summary("Define the contract details.")
    @DisplayName("Contract")
    @Optional
    public ContractFE frontOfficeCreateTicketContract;

    @Parameter
    @Summary("Define the required ticket properties.")
    @DisplayName("Properties")
    @Optional
    public PropertiesFE frontOfficeCreateTicketProperties;

    //End of CreateTicketBaseDto

    @Parameter
    @DisplayName("Template path")
    @Summary("Path to an existing template the ticket will be created from.")
    @Placement(order = 1)
    public String templateName;

    @Parameter
    @Summary("Define the brand for the document.")
    @Optional
    public String brand;

    @Parameter
    @Summary("A command related to the way of processing a JLD file.")
    @DisplayName("Commands")
    @Optional
    @NullSafe
    public List<CommandFE> frontOfficeCreateTicketCommands;

    @Parameter
    @Summary("Sets document's data context.")
    @DisplayName("Context")
    @NullSafe
    @Optional
    public List<ContextFE> frontOfficeCreateTicketContexts;

    @Parameter
    @Summary("Create clone of another document specified in create ticket request (index).")
    @DisplayName("Copy Of")
    @Optional
    public Integer frontOfficeCreateTicketCopyOf;

    @Parameter
    @DisplayName("Data Definitions")
    @Optional
    @NullSafe
    public List<DataDefinitionFE> frontOfficeCreateTicketDataDefinitions;

    @Parameter
    @Summary("Define the brand for the document.")
    @Optional
    public String description;

    @Parameter
    @Summary("If true, the ticket is created as a form document ticket.")
    @Optional
    public boolean pageDocument;

    public String getStateId() {
        return stateId;
    }

    public String getHolderValue() {
        return holderValue;
    }

    public HolderTypeFE getFrontOfficeCreateTicketHolderType() {
        return frontOfficeCreateTicketHolderType;
    }

    public boolean isAddAttachmentFromGlobalStorageEnabled() {
        return addAttachmentFromGlobalStorageEnabled;
    }

    public boolean isAsynchronousProcessing() {
        return asynchronousProcessing;
    }

    public boolean isMultipleRecord() {
        return multipleRecord;
    }

    public boolean isUploadAttachmentFromLocalDriveEnabled() {
        return uploadAttachmentFromLocalDriveEnabled;
    }

    public List<String> getFrontOfficeCreateTicketProductionActions() {
        return frontOfficeCreateTicketProductionActions;
    }

    public ApprovalProcessPathFE getFrontOfficeCreateTicketApprovalProcessPath() {
        return frontOfficeCreateTicketApprovalProcessPath;
    }

    public List<String> getFrontOfficeCreateTicketAttachments() {
        return frontOfficeCreateTicketAttachments;
    }

    public ContractFE getFrontOfficeCreateTicketContract() {
        return frontOfficeCreateTicketContract;
    }

    public PropertiesFE getFrontOfficeCreateTicketProperties() {
        return frontOfficeCreateTicketProperties;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getBrand() {
        return brand;
    }

    public List<CommandFE> getFrontOfficeCreateTicketCommands() {
        return frontOfficeCreateTicketCommands;
    }

    public List<ContextFE> getFrontOfficeCreateTicketContexts() {
        return frontOfficeCreateTicketContexts;
    }

    public Integer getFrontOfficeCreateTicketCopyOf() {
        return frontOfficeCreateTicketCopyOf;
    }

    public List<DataDefinitionFE> getFrontOfficeCreateTicketDataDefinitions() {
        return frontOfficeCreateTicketDataDefinitions;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPageDocument() {
        return pageDocument;
    }
}
