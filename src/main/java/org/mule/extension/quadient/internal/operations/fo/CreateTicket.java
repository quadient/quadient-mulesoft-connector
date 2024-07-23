package org.mule.extension.quadient.internal.operations.fo;

import com.quadient.mule.model.fo.TemplateDocumentData;

public class CreateTicket extends CreateTicketBaseDto {
    public TemplateDocumentData documentData;

    public CreateTicket() {
        this.type = "ticket";
        documentData = new TemplateDocumentData();
    }
}
