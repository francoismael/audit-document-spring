package com.audit.audit_document.application.dto;

import java.util.List;

public class TdrResponse {

    private Long id;

    private Long missionId;

    private String missionNumero;

    private String missionIntitule;

    private String missionObjet;

    private Long structureId;

    private String structureNom;

    private String contexte;

    private String competence;

    private String perimetre;

    private String periodeObservation;

    private String lieu;

    private String methodologieTravail;

    private String resultatsAttendus;

    private String livrables;

    private List<ObjectifResponse> objectifs;

    public TdrResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ObjectifResponse> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<ObjectifResponse> objectifs) {
        this.objectifs = objectifs;
    }
}