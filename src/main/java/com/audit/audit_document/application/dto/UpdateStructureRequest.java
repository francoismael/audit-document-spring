package com.audit.audit_document.application.dto;

public class UpdateStructureRequest {

    private String nom;
    private String descriptions;

    public UpdateStructureRequest() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}