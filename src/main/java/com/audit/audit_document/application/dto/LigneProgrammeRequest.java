package com.audit.audit_document.application.dto;

public class LigneProgrammeRequest {

    private Long id;
    private Long objectifId;

    private String tacheOperation;
    private String faiblesseAConfirmer;
    private String responsable;
    private String frequence;
    private String typeControle;
    private String domaineCycle;
    private String risque;
    private String procedureTest;
    private String echantillonDescription;
    private String techniqueAudit;
    private String techniqueEchantillonnage;

    public LigneProgrammeRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectifId() {
        return objectifId;
    }

    public void setObjectifId(Long objectifId) {
        this.objectifId = objectifId;
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

    public String getTypeControle() {
        return typeControle;
    }

    public void setTypeControle(String typeControle) {
        this.typeControle = typeControle;
    }

    public String getDomaineCycle() {
        return domaineCycle;
    }

    public void setDomaineCycle(String domaineCycle) {
        this.domaineCycle = domaineCycle;
    }

    public String getRisque() {
        return risque;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getProcedureTest() {
        return procedureTest;
    }

    public void setProcedureTest(String procedureTest) {
        this.procedureTest = procedureTest;
    }

    public String getEchantillonDescription() {
        return echantillonDescription;
    }

    public void setEchantillonDescription(String echantillonDescription) {
        this.echantillonDescription = echantillonDescription;
    }

    public String getTechniqueAudit() {
        return techniqueAudit;
    }

    public void setTechniqueAudit(String techniqueAudit) {
        this.techniqueAudit = techniqueAudit;
    }

    public String getTechniqueEchantillonnage() {
        return techniqueEchantillonnage;
    }

    public void setTechniqueEchantillonnage(String techniqueEchantillonnage) {
        this.techniqueEchantillonnage = techniqueEchantillonnage;
    }
}