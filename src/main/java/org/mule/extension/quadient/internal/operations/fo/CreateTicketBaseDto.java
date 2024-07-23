package org.mule.extension.quadient.internal.operations.fo;

import com.quadient.mule.model.fo.ApprovalProcessPath;
import com.quadient.mule.model.fo.Contract;
import com.quadient.mule.model.fo.Holder;
import com.quadient.mule.model.fo.Properties;

import java.util.List;

abstract class CreateTicketBaseDto {
    public String stateId;
    public Holder holder;
    public String type;
    public boolean addAttachmentFromGlobalStorageEnabled;
    public boolean asynchronousProcessing;
    public boolean multipleRecord;
    public boolean uploadAttachmentFromLocalDriveEnabled;
    public List<String> productionActions;
    public ApprovalProcessPath approvalProcessPath;
    public List<String> attachments;
    public Contract contract;
    public Properties properties;
}
