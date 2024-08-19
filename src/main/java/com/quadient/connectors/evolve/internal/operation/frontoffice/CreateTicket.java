package com.quadient.connectors.evolve.internal.operation.frontoffice;


import com.quadient.connectors.generated.model.fo.TemplateDocumentData;

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
