package com.audit.audit_document.application.dto;

import java.util.List;

public class CreateDocumentFieldsRequest {

    private Long documentTypeId;

    private List<DocumentFieldItemRequest> fields;

    public CreateDocumentFieldsRequest() {
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public List<DocumentFieldItemRequest> getFields() {
        return fields;
    }

    public void setFields(List<DocumentFieldItemRequest> fields) {
        this.fields = fields;
    }
}