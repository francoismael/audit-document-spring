package com.audit.audit_document.application.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class DocumentGenereResponse {

    private Long id;

    private Long documentTypeId;
    private String documentTypeCode;
    private String documentTypeNom;

    private Long missionId;
    private String missionNumero;
    private String missionIntitule;

    private String nomFichier;
    private Long taille;
    private Integer templateVersion;
    private LocalDateTime dateGeneration;

    private Map<String, Object> valeurs;

    public DocumentGenereResponse() {
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

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public String getDocumentTypeNom() {
        return documentTypeNom;
    }

    public void setDocumentTypeNom(String documentTypeNom) {
        this.documentTypeNom = documentTypeNom;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionNumero() {
        return missionNumero;
    }

    public void setMissionNumero(String missionNumero) {
        this.missionNumero = missionNumero;
    }

    public String getMissionIntitule() {
        return missionIntitule;
    }

    public void setMissionIntitule(String missionIntitule) {
        this.missionIntitule = missionIntitule;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Integer getTemplateVersion() {
        return templateVersion;
    }

    public void setTemplateVersion(Integer templateVersion) {
        this.templateVersion = templateVersion;
    }

    public LocalDateTime getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public Map<String, Object> getValeurs() {
        return valeurs;
    }

    public void setValeurs(Map<String, Object> valeurs) {
        this.valeurs = valeurs;
    }
}
