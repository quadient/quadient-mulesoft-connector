package org.mule.extension.quadient.internal.operation.frontoffice;

import com.quadient.mule.model.fo.TemplateDocumentData;

public class CreateTicket extends CreateTicketBaseDto {
    private TemplateDocumentData documentData;

    public CreateTicket() {
        this.setType("ticket");
        documentData = new TemplateDocumentData();
    }

    public TemplateDocumentData getDocumentData() {
        return documentData;
    }
}
