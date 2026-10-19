package com.audit.audit_document.application.dto;

import java.time.LocalDateTime;

public class DocumentTemplateResponse {

    private Long id;

    private Long documentTypeId;

    private String documentTypeNom;

    private String nomFichierOriginal;

    private String nomFichierStockage;

    private String cheminFichier;

    private String typeMime;

    private Long taille;

    private Integer version;

    private Boolean actif;

    private LocalDateTime dateUpload;

    public DocumentTemplateResponse() {
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

    public void setDocumentTypeId(
            Long documentTypeId) {

        this.documentTypeId = documentTypeId;
    }

    public String getDocumentTypeNom() {
        return documentTypeNom;
    }

    public void setDocumentTypeNom(
            String documentTypeNom) {

        this.documentTypeNom =
                documentTypeNom;
    }

    public String getNomFichierOriginal() {
        return nomFichierOriginal;
    }

    public void setNomFichierOriginal(
            String nomFichierOriginal) {

        this.nomFichierOriginal =
                nomFichierOriginal;
    }

    public String getNomFichierStockage() {
        return nomFichierStockage;
    }

    public void setNomFichierStockage(
            String nomFichierStockage) {

        this.nomFichierStockage =
                nomFichierStockage;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(
            String cheminFichier) {

        this.cheminFichier =
                cheminFichier;
    }

    public String getTypeMime() {
        return typeMime;
    }

    public void setTypeMime(String typeMime) {
        this.typeMime = typeMime;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(
            LocalDateTime dateUpload) {

        this.dateUpload = dateUpload;
    }
}