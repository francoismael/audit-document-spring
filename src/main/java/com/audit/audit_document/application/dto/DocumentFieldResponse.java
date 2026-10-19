package com.audit.audit_document.application.dto;

public class DocumentFieldResponse {

    private Long id;

    private Long documentTypeId;

    private String documentTypeNom;

    private String nom;

    private String libelle;

    private String type;

    private Boolean obligatoire;

    private Integer ordre;

    public DocumentFieldResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getDocumentTypeNom() {
        return documentTypeNom;
    }

    public void setDocumentTypeNom(String documentTypeNom) {
        this.documentTypeNom = documentTypeNom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }
}