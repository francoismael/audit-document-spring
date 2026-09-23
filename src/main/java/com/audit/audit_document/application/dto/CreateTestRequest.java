package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.List;

public class CreateTestRequest {

    private Long ligneProgrammeId;

    private LocalDate dateTest;

    private String procedureRealisee;

    private String resumeAnomalies;

    private String resultatTest;

    private Boolean risqueMaitrise;

    private String recommandations;

    private String commentairesChefMission;

    private String commentairesSuperviseur;

    private Long auditeurMissionPersonneId;

    private LocalDate dateAudit;

    private Long chefMissionMissionPersonneId;

    private LocalDate dateRevueChefMission;

    private Long superviseurMissionPersonneId;

    private LocalDate dateRevueSuperviseur;

    private List<EchantillonRequest> echantillons;

    public CreateTestRequest() {
    }

    public Long getLigneProgrammeId() {
        return ligneProgrammeId;
    }

    public void setLigneProgrammeId(Long ligneProgrammeId) {
        this.ligneProgrammeId = ligneProgrammeId;
    }

    public LocalDate getDateTest() {
        return dateTest;
    }

    public void setDateTest(LocalDate dateTest) {
        this.dateTest = dateTest;
    }

    public String getProcedureRealisee() {
        return procedureRealisee;
    }

    public void setProcedureRealisee(String procedureRealisee) {
        this.procedureRealisee = procedureRealisee;
    }

    public String getResumeAnomalies() {
        return resumeAnomalies;
    }

    public void setResumeAnomalies(String resumeAnomalies) {
        this.resumeAnomalies = resumeAnomalies;
    }

    public String getResultatTest() {
        return resultatTest;
    }

    public void setResultatTest(String resultatTest) {
        this.resultatTest = resultatTest;
    }

    public Boolean getRisqueMaitrise() {
        return risqueMaitrise;
    }

    public void setRisqueMaitrise(Boolean risqueMaitrise) {
        this.risqueMaitrise = risqueMaitrise;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public String getCommentairesChefMission() {
        return commentairesChefMission;
    }

    public void setCommentairesChefMission(String commentairesChefMission) {
        this.commentairesChefMission = commentairesChefMission;
    }

    public String getCommentairesSuperviseur() {
        return commentairesSuperviseur;
    }

    public void setCommentairesSuperviseur(String commentairesSuperviseur) {
        this.commentairesSuperviseur = commentairesSuperviseur;
    }

    public Long getAuditeurMissionPersonneId() {
        return auditeurMissionPersonneId;
    }

    public void setAuditeurMissionPersonneId(Long auditeurMissionPersonneId) {
        this.auditeurMissionPersonneId = auditeurMissionPersonneId;
    }

    public LocalDate getDateAudit() {
        return dateAudit;
    }

    public void setDateAudit(LocalDate dateAudit) {
        this.dateAudit = dateAudit;
    }

    public Long getChefMissionMissionPersonneId() {
        return chefMissionMissionPersonneId;
    }

    public void setChefMissionMissionPersonneId(Long chefMissionMissionPersonneId) {
        this.chefMissionMissionPersonneId = chefMissionMissionPersonneId;
    }

    public LocalDate getDateRevueChefMission() {
        return dateRevueChefMission;
    }

    public void setDateRevueChefMission(LocalDate dateRevueChefMission) {
        this.dateRevueChefMission = dateRevueChefMission;
    }

    public Long getSuperviseurMissionPersonneId() {
        return superviseurMissionPersonneId;
    }

    public void setSuperviseurMissionPersonneId(Long superviseurMissionPersonneId) {
        this.superviseurMissionPersonneId = superviseurMissionPersonneId;
    }

    public LocalDate getDateRevueSuperviseur() {
        return dateRevueSuperviseur;
    }

    public void setDateRevueSuperviseur(LocalDate dateRevueSuperviseur) {
        this.dateRevueSuperviseur = dateRevueSuperviseur;
    }

    public List<EchantillonRequest> getEchantillons() {
        return echantillons;
    }

    public void setEchantillons(List<EchantillonRequest> echantillons) {
        this.echantillons = echantillons;
    }
}