package com.audit.audit_document.application.dto;

public class PvAffirmationParticipantRequest {

    private Long missionPersonneId;

    private String typeParticipant;

    public PvAffirmationParticipantRequest() {
    }

    public Long getMissionPersonneId() {
        return missionPersonneId;
    }

    public void setMissionPersonneId(
            Long missionPersonneId) {

        this.missionPersonneId =
                missionPersonneId;
    }

    public String getTypeParticipant() {
        return typeParticipant;
    }

    public void setTypeParticipant(
            String typeParticipant) {

        this.typeParticipant =
                typeParticipant;
    }
}