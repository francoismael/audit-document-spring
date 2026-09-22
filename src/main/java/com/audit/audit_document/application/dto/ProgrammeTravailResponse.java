package com.audit.audit_document.application.dto;

import java.util.List;

public class ProgrammeTravailResponse {

    private Long id;

    private Long missionId;
    private String missionNumero;
    private String missionIntitule;
    private String missionObjet;

    private Long structureId;
    private String structureNom;

    private List<LigneProgrammeResponse> lignes;

    public ProgrammeTravailResponse() {
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

    public List<LigneProgrammeResponse> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneProgrammeResponse> lignes) {
        this.lignes = lignes;
    }
}