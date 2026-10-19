package com.audit.audit_document.application.dto;

import java.time.LocalDateTime;

public class DocumentTypeResponse {

    private Long id;

    private String code;

    private String nom;

    private String description;

    private Boolean actif;

    private LocalDateTime dateCreation;

    public DocumentTypeResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(
            LocalDateTime dateCreation) {

        this.dateCreation = dateCreation;
    }
}