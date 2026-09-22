package com.audit.audit_document.application.dto;

import java.util.List;

public class CreateProgrammeTravailRequest {

    private Long missionId;
    private List<LigneProgrammeRequest> lignes;

    public CreateProgrammeTravailRequest() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public List<LigneProgrammeRequest> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneProgrammeRequest> lignes) {
        this.lignes = lignes;
    }
}