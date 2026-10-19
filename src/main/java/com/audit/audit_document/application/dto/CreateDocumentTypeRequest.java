package com.audit.audit_document.application.dto;

public class CreateDocumentTypeRequest {

    private String code;

    private String nom;

    private String description;

    public CreateDocumentTypeRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }
}