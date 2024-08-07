package org.mule.extension.quadient.internal.operation.frontOffice;

import com.quadient.mule.model.fo.TemplateDocumentData;

public class CreateTicket extends CreateTicketBaseDto {
    public TemplateDocumentData documentData;

    public CreateTicket() {
        this.type = "ticket";
        documentData = new TemplateDocumentData();
    }
}
