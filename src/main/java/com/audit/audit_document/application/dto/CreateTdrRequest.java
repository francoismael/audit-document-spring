package com.audit.audit_document.application.dto;

import java.util.List;

public class CreateTdrRequest {

    private Long missionId;

    private String contexte;

    private String competence;

    private String perimetre;

    private String periodeObservation;

    private String lieu;

    private String methodologieTravail;

    private String resultatsAttendus;

    private String livrables;

    private List<ObjectifRequest> objectifs;

    public CreateTdrRequest() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getContexte() {
        return contexte;
    }

    public void setContexte(String contexte) {
        this.contexte = contexte;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getPerimetre() {
        return perimetre;
    }

    public void setPerimetre(String perimetre) {
        this.perimetre = perimetre;
    }

    public String getPeriodeObservation() {
        return periodeObservation;
    }

    public void setPeriodeObservation(String periodeObservation) {
        this.periodeObservation = periodeObservation;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMethodologieTravail() {
        return methodologieTravail;
    }

    public void setMethodologieTravail(String methodologieTravail) {
        this.methodologieTravail = methodologieTravail;
    }

    public String getResultatsAttendus() {
        return resultatsAttendus;
    }

    public void setResultatsAttendus(String resultatsAttendus) {
        this.resultatsAttendus = resultatsAttendus;
    }

    public String getLivrables() {
        return livrables;
    }

    public void setLivrables(String livrables) {
        this.livrables = livrables;
    }

    public List<ObjectifRequest> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<ObjectifRequest> objectifs) {
        this.objectifs = objectifs;
    }
}