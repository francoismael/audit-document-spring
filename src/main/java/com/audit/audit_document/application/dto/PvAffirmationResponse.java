package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PvAffirmationResponse {

    private Long id;

    private Long missionId;

    private String missionNumero;

    private String missionIntitule;

    private String missionObjet;

    private String structureNom;

    private LocalDate dateEntretien;

    private String lieu;

    private String observationsComplementaires;

    private List<PvAffirmationParticipantResponse>
            participants =
            new ArrayList<PvAffirmationParticipantResponse>();

    private List<PvAffirmationLigneResponse>
            lignes =
            new ArrayList<PvAffirmationLigneResponse>();

    public PvAffirmationResponse() {
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

    public void setMissionNumero(
            String missionNumero) {

        this.missionNumero = missionNumero;
    }

    public String getMissionIntitule() {
        return missionIntitule;
    }

    public void setMissionIntitule(
            String missionIntitule) {

        this.missionIntitule = missionIntitule;
    }

    public String getMissionObjet() {
        return missionObjet;
    }

    public void setMissionObjet(
            String missionObjet) {

        this.missionObjet = missionObjet;
    }

    public String getStructureNom() {
        return structureNom;
    }

    public void setStructureNom(
            String structureNom) {

        this.structureNom = structureNom;
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

    public List<PvAffirmationParticipantResponse>
    getParticipants() {

        return participants;
    }

    public void setParticipants(
            List<PvAffirmationParticipantResponse>
                    participants) {

        this.participants = participants;
    }

    public List<PvAffirmationLigneResponse>
    getLignes() {

        return lignes;
    }

    public void setLignes(
            List<PvAffirmationLigneResponse> lignes) {

        this.lignes = lignes;
    }
}