package com.audit.audit_document.application.dto;

import java.util.List;

public class DocumentTemplateValidationResponse {

    private Long documentTypeId;

    private Long templateId;

    private String templateName;

    private List<String> variablesInTemplate;

    private List<String> fieldsInDatabase;

    private List<String> missingFields;

    private List<String> unknownVariables;

    private boolean valid;

    public DocumentTemplateValidationResponse() {
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<String> getVariablesInTemplate() {
        return variablesInTemplate;
    }

    public void setVariablesInTemplate(
            List<String> variablesInTemplate) {

        this.variablesInTemplate = variablesInTemplate;
    }

    public List<String> getFieldsInDatabase() {
        return fieldsInDatabase;
    }

    public void setFieldsInDatabase(
            List<String> fieldsInDatabase) {

        this.fieldsInDatabase = fieldsInDatabase;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public void setMissingFields(
            List<String> missingFields) {

        this.missingFields = missingFields;
    }

    public List<String> getUnknownVariables() {
        return unknownVariables;
    }

    public void setUnknownVariables(
            List<String> unknownVariables) {

        this.unknownVariables = unknownVariables;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}