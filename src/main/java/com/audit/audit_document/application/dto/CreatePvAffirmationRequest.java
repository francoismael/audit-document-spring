package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreatePvAffirmationRequest {

    private Long missionId;

    private LocalDate dateEntretien;

    private String lieu;

    private String observationsComplementaires;

    private List<PvAffirmationParticipantRequest> participants =
            new ArrayList<PvAffirmationParticipantRequest>();

    private List<PvAffirmationLigneRequest> lignes =
            new ArrayList<PvAffirmationLigneRequest>();

    public CreatePvAffirmationRequest() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public LocalDate getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(
            LocalDate dateEntretien) {

        this.dateEntretien = dateEntretien;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getObservationsComplementaires() {
        return observationsComplementaires;
    }

    public void setObservationsComplementaires(
            String observationsComplementaires) {

        this.observationsComplementaires =
                observationsComplementaires;
    }

    public List<PvAffirmationParticipantRequest>
    getParticipants() {

        return participants;
    }

    public void setParticipants(
            List<PvAffirmationParticipantRequest>
                    participants) {

        this.participants = participants;
    }

    public List<PvAffirmationLigneRequest> getLignes() {

        return lignes;
    }

    public void setLignes(
            List<PvAffirmationLigneRequest> lignes) {

        this.lignes = lignes;
    }
}