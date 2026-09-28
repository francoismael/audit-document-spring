package com.audit.audit_document.application.dto;

import java.time.LocalDate;

public class CreateRapportAuditRequest {

    private Long missionId;

    private String motifs;

    private String nature;

    private String versionRapport;

    private LocalDate dateEmission;

    private String proprietaireDocument;

    private String destinataires;

    private String classification;

    public CreateRapportAuditRequest() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
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

    public void setProprietaireDocument(String proprietaireDocument) {
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