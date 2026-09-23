package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.List;

public class TestResponse {

    private Long id;

    private String reference;

    private Long ligneProgrammeId;

    // Informations du contrôle
    private String numeroControle;

    // Informations de l'objectif
    private Long objectifId;
    private String objectifNumero;
    private String objectifDescription;

    // Informations de la mission
    private String missionNumero;
    private String missionIntitule;
    private String missionObjet;

    // Informations de la structure auditée
    private String structureNom;

    // Informations du programme de travail
    private String lieu;
    private String periodeObservation;
    private String tacheOperation;
    private String faiblesseAConfirmer;
    private String risque;
    private String domaineCycle;
    private String typeControle;
    private String responsable;
    private String frequence;

    // Informations d'échantillonnage prévues dans le programme
    private String echantillonDescription;
    private String techniqueEchantillonnage;

    // Procédure prévue
    private String procedureTest;

    // Informations propres au test
    private LocalDate dateTest;
    private String procedureRealisee;
    private String resumeAnomalies;
    private String resultatTest;
    private Boolean risqueMaitrise;
    private String recommandations;

    // Commentaires
    private String commentairesChefMission;
    private String commentairesSuperviseur;

    // Contrôle qualité : auditeur
    private Long auditeurMissionPersonneId;
    private String auditeurNom;
    private String auditeurPrenom;
    private LocalDate dateAudit;

    // Contrôle qualité : chef de mission
    private Long chefMissionMissionPersonneId;
    private String chefMissionNom;
    private String chefMissionPrenom;
    private LocalDate dateRevueChefMission;

    // Contrôle qualité : superviseur
    private Long superviseurMissionPersonneId;
    private String superviseurNom;
    private String superviseurPrenom;
    private LocalDate dateRevueSuperviseur;

    // Échantillons
    private List<EchantillonResponse> echantillons;

    public TestResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getLigneProgrammeId() {
        return ligneProgrammeId;
    }

    public void setLigneProgrammeId(Long ligneProgrammeId) {
        this.ligneProgrammeId = ligneProgrammeId;
    }

    public String getNumeroControle() {
        return numeroControle;
    }

    public void setNumeroControle(String numeroControle) {
        this.numeroControle = numeroControle;
    }

    public Long getObjectifId() {
        return objectifId;
    }

    public void setObjectifId(Long objectifId) {
        this.objectifId = objectifId;
    }

    public String getObjectifNumero() {
        return objectifNumero;
    }

    public void setObjectifNumero(String objectifNumero) {
        this.objectifNumero = objectifNumero;
    }

    public String getObjectifDescription() {
        return objectifDescription;
    }

    public void setObjectifDescription(String objectifDescription) {
        this.objectifDescription = objectifDescription;
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

    public String getStructureNom() {
        return structureNom;
    }

    public void setStructureNom(String structureNom) {
        this.structureNom = structureNom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getPeriodeObservation() {
        return periodeObservation;
    }

    public void setPeriodeObservation(String periodeObservation) {
        this.periodeObservation = periodeObservation;
    }

    public String getTacheOperation() {
        return tacheOperation;
    }

    public void setTacheOperation(String tacheOperation) {
        this.tacheOperation = tacheOperation;
    }

    public String getFaiblesseAConfirmer() {
        return faiblesseAConfirmer;
    }

    public void setFaiblesseAConfirmer(String faiblesseAConfirmer) {
        this.faiblesseAConfirmer = faiblesseAConfirmer;
    }

    public String getRisque() {
        return risque;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getDomaineCycle() {
        return domaineCycle;
    }

    public void setDomaineCycle(String domaineCycle) {
        this.domaineCycle = domaineCycle;
    }

    public String getTypeControle() {
        return typeControle;
    }

    public void setTypeControle(String typeControle) {
        this.typeControle = typeControle;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getFrequence() {
        return frequence;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public String getEchantillonDescription() {
        return echantillonDescription;
    }

    public void setEchantillonDescription(String echantillonDescription) {
        this.echantillonDescription = echantillonDescription;
    }

    public String getTechniqueEchantillonnage() {
        return techniqueEchantillonnage;
    }

    public void setTechniqueEchantillonnage(String techniqueEchantillonnage) {
        this.techniqueEchantillonnage = techniqueEchantillonnage;
    }

    public String getProcedureTest() {
        return procedureTest;
    }

    public void setProcedureTest(String procedureTest) {
        this.procedureTest = procedureTest;
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

    public String getAuditeurNom() {
        return auditeurNom;
    }

    public void setAuditeurNom(String auditeurNom) {
        this.auditeurNom = auditeurNom;
    }

    public String getAuditeurPrenom() {
        return auditeurPrenom;
    }

    public void setAuditeurPrenom(String auditeurPrenom) {
        this.auditeurPrenom = auditeurPrenom;
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

    public void setChefMissionMissionPersonneId(
            Long chefMissionMissionPersonneId) {

        this.chefMissionMissionPersonneId =
                chefMissionMissionPersonneId;
    }

    public String getChefMissionNom() {
        return chefMissionNom;
    }

    public void setChefMissionNom(String chefMissionNom) {
        this.chefMissionNom = chefMissionNom;
    }

    public String getChefMissionPrenom() {
        return chefMissionPrenom;
    }

    public void setChefMissionPrenom(String chefMissionPrenom) {
        this.chefMissionPrenom = chefMissionPrenom;
    }

    public LocalDate getDateRevueChefMission() {
        return dateRevueChefMission;
    }

    public void setDateRevueChefMission(
            LocalDate dateRevueChefMission) {

        this.dateRevueChefMission =
                dateRevueChefMission;
    }

    public Long getSuperviseurMissionPersonneId() {
        return superviseurMissionPersonneId;
    }

    public void setSuperviseurMissionPersonneId(
            Long superviseurMissionPersonneId) {

        this.superviseurMissionPersonneId =
                superviseurMissionPersonneId;
    }

    public String getSuperviseurNom() {
        return superviseurNom;
    }

    public void setSuperviseurNom(String superviseurNom) {
        this.superviseurNom = superviseurNom;
    }

    public String getSuperviseurPrenom() {
        return superviseurPrenom;
    }

    public void setSuperviseurPrenom(String superviseurPrenom) {
        this.superviseurPrenom = superviseurPrenom;
    }

    public LocalDate getDateRevueSuperviseur() {
        return dateRevueSuperviseur;
    }

    public void setDateRevueSuperviseur(
            LocalDate dateRevueSuperviseur) {

        this.dateRevueSuperviseur =
                dateRevueSuperviseur;
    }

    public List<EchantillonResponse> getEchantillons() {
        return echantillons;
    }

    public void setEchantillons(
            List<EchantillonResponse> echantillons) {

        this.echantillons = echantillons;
    }
}