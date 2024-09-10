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
    @Summary("Initial state of the ticket, the state must be in the approval process.")
    @Example("S_simple_scenario_writer_assigned")
    String stateId;

    @Parameter
    @Summary("Define the holder of the ticket.")
    @Example("lee@vital.com")
    String holderValue;

    @Parameter
    @Summary("Define the holder of the ticket.")
    @DisplayName("Holder Type")
    HolderTypeFE createTicketHolderType;

    @Parameter
    @Summary("Name of the processing pipeline to be run.")
    @Optional
    boolean addAttachmentFromGlobalStorageEnabled;

    @Parameter
    @Summary("Determines whether the ticket will be created asynchronously.")
    @Optional
    boolean asynchronousProcessing;

    @Parameter
    @Summary("Determines whether the ticket will be handled as a multipleRecord ticket (for multiple recipients).")
    @Optional
    boolean multipleRecord;

    @Parameter
    @Summary("Determines whether a user can add attachments from a local drive.")
    @Optional
    boolean uploadAttachmentFromLocalDriveEnabled;

    @Parameter
    @Example("SMS, EMAIL, EMAIL_WITH_ATTACHMENT, PRINT")
    @Summary("Determines which production actions will be available in the ticket.")
    @Optional
    @NullSafe
    List<String> productionActions;

    @Parameter
    @Summary("This parameter overrides the default selection of the ticket approval process.")
    @DisplayName("Approval Process Path")
    @Optional
    ApprovalProcessPathFE createTicketApprovalProcessPath;

    @Parameter
    @Example("'Gift Voucher - GPK Adventure - Africa.jpg', 'Gift Voucher - Froodeco - Bouquet.pdf'")
    @Summary("Attachments to be added to the ticket.")
    @Optional
    @NullSafe
    List<String> attachments;

    @Parameter
    @Summary("Define the contract details.")
    @DisplayName("Contract")
    @Optional
    ContractFE createTicketContract;

    @Parameter
    @Summary("Define the required ticket properties.")
    @DisplayName("Properties")
    @Optional
    PropertiesFE createTicketProperties;

    //End of CreateTicketBaseDto

    @Parameter
    @Summary("Path to an existing template the ticket will be created from.")
    @Placement(order = 1)
    String templateName;

    @Parameter
    @Summary("Define the brand for the document.")
    @Optional
    String brand;

    @Parameter
    @Summary("A command related to the way of processing a JLD file.")
    @Optional
    @NullSafe
    List<CommandFE> commands;

    @Parameter
    @Summary("Sets document's data context.")
    @DisplayName("Context")
    @NullSafe
    @Optional
    List<ContextFE> createTicketContexts;

    @Parameter
    @Summary("Create clone of another document specified in create ticket request (index).")
    @DisplayName("Copy Of")
    @Optional
    Integer createTicketCopyOf;

    @Parameter
    @Optional
    @NullSafe
    List<DataDefinitionFE> dataDefinitions;

    @Parameter
    @Summary("Define the brand for the document.")
    @Optional
    String description;

    @Parameter
    @Summary("If true, the ticket is created as a form document ticket.")
    @Optional
    boolean pageDocument;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getHolderValue() {
        return holderValue;
    }

    public void setHolderValue(String holderValue) {
        this.holderValue = holderValue;
    }

    public HolderTypeFE getCreateTicketHolderType() {
        return createTicketHolderType;
    }

    public void setCreateTicketHolderType(HolderTypeFE createTicketHolderType) {
        this.createTicketHolderType = createTicketHolderType;
    }

    public boolean isAddAttachmentFromGlobalStorageEnabled() {
        return addAttachmentFromGlobalStorageEnabled;
    }

    public void setAddAttachmentFromGlobalStorageEnabled(boolean addAttachmentFromGlobalStorageEnabled) {
        this.addAttachmentFromGlobalStorageEnabled = addAttachmentFromGlobalStorageEnabled;
    }

    public boolean isAsynchronousProcessing() {
        return asynchronousProcessing;
    }

    public void setAsynchronousProcessing(boolean asynchronousProcessing) {
        this.asynchronousProcessing = asynchronousProcessing;
    }

    public boolean isMultipleRecord() {
        return multipleRecord;
    }

    public void setMultipleRecord(boolean multipleRecord) {
        this.multipleRecord = multipleRecord;
    }

    public boolean isUploadAttachmentFromLocalDriveEnabled() {
        return uploadAttachmentFromLocalDriveEnabled;
    }

    public void setUploadAttachmentFromLocalDriveEnabled(boolean uploadAttachmentFromLocalDriveEnabled) {
        this.uploadAttachmentFromLocalDriveEnabled = uploadAttachmentFromLocalDriveEnabled;
    }

    public List<String> getProductionActions() {
        return productionActions;
    }

    public void setProductionActions(List<String> productionActions) {
        this.productionActions = productionActions;
    }

    public ApprovalProcessPathFE getCreateTicketApprovalProcessPath() {
        return createTicketApprovalProcessPath;
    }

    public void setCreateTicketApprovalProcessPath(ApprovalProcessPathFE createTicketApprovalProcessPath) {
        this.createTicketApprovalProcessPath = createTicketApprovalProcessPath;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public ContractFE getCreateTicketContract() {
        return createTicketContract;
    }

    public void setCreateTicketContract(ContractFE createTicketContract) {
        this.createTicketContract = createTicketContract;
    }

    public PropertiesFE getCreateTicketProperties() {
        return createTicketProperties;
    }

    public void setCreateTicketProperties(PropertiesFE createTicketProperties) {
        this.createTicketProperties = createTicketProperties;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<CommandFE> getCommands() {
        return commands;
    }

    public void setCommands(List<CommandFE> commands) {
        this.commands = commands;
    }

    public List<ContextFE> getCreateTicketContexts() {
        return createTicketContexts;
    }

    public void setCreateTicketContexts(List<ContextFE> createTicketContexts) {
        this.createTicketContexts = createTicketContexts;
    }

    public Integer getCreateTicketCopyOf() {
        return createTicketCopyOf;
    }

    public void setCreateTicketCopyOf(Integer createTicketCopyOf) {
        this.createTicketCopyOf = createTicketCopyOf;
    }

    public List<DataDefinitionFE> getDataDefinitions() {
        return dataDefinitions;
    }

    public void setDataDefinitions(List<DataDefinitionFE> dataDefinitions) {
        this.dataDefinitions = dataDefinitions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPageDocument() {
        return pageDocument;
    }

    public void setPageDocument(boolean pageDocument) {
        this.pageDocument = pageDocument;
    }
}
