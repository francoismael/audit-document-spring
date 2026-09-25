package com.audit.audit_document.application.dto;

import java.util.ArrayList;
import java.util.List;

public class TableauRecommandationsResponse {

    private Long missionId;

    private String missionIntitule;

    private Long structureId;

    private String structureNom;

    private List<RecommandationRegroupeeResponse> lignes =
            new ArrayList<>();

    public TableauRecommandationsResponse() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionIntitule() {
        return missionIntitule;
    }

    public void setMissionIntitule(String missionIntitule) {
        this.missionIntitule = missionIntitule;
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

    public List<RecommandationRegroupeeResponse> getLignes() {
        return lignes;
    }

    public void setLignes(
            List<RecommandationRegroupeeResponse> lignes) {

        this.lignes = lignes;
    }
}