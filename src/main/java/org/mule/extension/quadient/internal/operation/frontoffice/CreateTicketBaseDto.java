package org.mule.extension.quadient.internal.operation.frontoffice;

import com.quadient.mule.model.fo.ApprovalProcessPath;
import com.quadient.mule.model.fo.Contract;
import com.quadient.mule.model.fo.Holder;
import com.quadient.mule.model.fo.Properties;

import java.util.List;

abstract class CreateTicketBaseDto {
    private String stateId;
    private Holder holder;
    private String type;
    private boolean addAttachmentFromGlobalStorageEnabled;
    private boolean asynchronousProcessing;
    private boolean multipleRecord;
    private boolean uploadAttachmentFromLocalDriveEnabled;
    private List<String> productionActions;
    private ApprovalProcessPath approvalProcessPath;
    private List<String> attachments;
    private Contract contract;
    private Properties properties;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
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

    public ApprovalProcessPath getApprovalProcessPath() {
        return approvalProcessPath;
    }

    public void setApprovalProcessPath(ApprovalProcessPath approvalProcessPath) {
        this.approvalProcessPath = approvalProcessPath;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
