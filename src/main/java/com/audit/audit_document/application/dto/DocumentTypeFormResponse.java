package com.audit.audit_document.application.dto;

import java.util.List;

public class DocumentTypeFormResponse {

    private DocumentTypeResponse documentType;

    private DocumentTemplateResponse template;

    private List<DocumentFieldResponse> fields;

    public DocumentTypeFormResponse() {
    }

    public DocumentTypeResponse getDocumentType() {
        return documentType;
    }

    public void setDocumentType(
            DocumentTypeResponse documentType) {

        this.documentType = documentType;
    }

    public DocumentTemplateResponse getTemplate() {
        return template;
    }

    public void setTemplate(
            DocumentTemplateResponse template) {

        this.template = template;
    }

    public List<DocumentFieldResponse> getFields() {
        return fields;
    }

    public void setFields(
            List<DocumentFieldResponse> fields) {

        this.fields = fields;
    }
}