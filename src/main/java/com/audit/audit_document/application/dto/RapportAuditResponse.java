package com.audit.audit_document.application.dto;

import java.time.LocalDate;

public class RapportAuditResponse {

    private Long rapportId;

    private Long missionId;
    private String missionNumero;
    private String missionIntitule;
    private String missionObjet;
    private LocalDate missionDateSignature;

    private Long structureId;
    private String structureNom;

    private String titre;

    private String motifs;
    private String nature;

    private String referenceOm;
    private LocalDate dateOm;

    private String versionRapport;
    private LocalDate dateEmission;

    private String proprietaireDocument;
    private String destinataires;
    private String classification;

    public RapportAuditResponse() {
    }

    public Long getRapportId() {
        return rapportId;
    }

    public void setRapportId(Long rapportId) {
        this.rapportId = rapportId;
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

    public String getMissionObjet() {
        return missionObjet;
    }

    public void setMissionObjet(String missionObjet) {
        this.missionObjet = missionObjet;
    }

    public LocalDate getMissionDateSignature() {
        return missionDateSignature;
    }

    public void setMissionDateSignature(
            LocalDate missionDateSignature) {

        this.missionDateSignature = missionDateSignature;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

    public String getStructureNom() {
        return structureNom;
    }

    public void setStructureNom(String structureNom) {
        this.structureNom = structureNom;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMotifs() {
        return motifs;
    }

    public void setMotifs(String motifs) {
        this.motifs = motifs;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getReferenceOm() {
        return referenceOm;
    }

    public void setReferenceOm(String referenceOm) {
        this.referenceOm = referenceOm;
    }

    public LocalDate getDateOm() {
        return dateOm;
    }

    public void setDateOm(LocalDate dateOm) {
        this.dateOm = dateOm;
    }

    public String getVersionRapport() {
        return versionRapport;
    }

    public void setVersionRapport(String versionRapport) {
        this.versionRapport = versionRapport;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public String getProprietaireDocument() {
        return proprietaireDocument;
    }

    public void setProprietaireDocument(
            String proprietaireDocument) {

        this.proprietaireDocument = proprietaireDocument;
    }

    public String getDestinataires() {
        return destinataires;
    }

    public void setDestinataires(String destinataires) {
        this.destinataires = destinataires;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}