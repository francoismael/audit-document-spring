package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReunionResponse {

    private Long id;

    private Long missionId;

    private String missionNumero;

    private String missionIntitule;

    private String type;

    private LocalDate dateReunion;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private String lieu;

    private String observations;

    private String pointsDaiIntroduction;

    private String pointsDaiPresentationMission;

    private String pointsDaiMethodologie;

    private String pointsInterlocuteursIntroduction;

    private String pointsInterlocuteursProcessus;

    private String pointsInterlocuteursOrganisation;

    private List<ReunionPersonneResponse> participants;

    public ReunionResponse() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(LocalDate dateReunion) {
        this.dateReunion = dateReunion;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getPointsDaiIntroduction() {
        return pointsDaiIntroduction;
    }

    public void setPointsDaiIntroduction(String pointsDaiIntroduction) {
        this.pointsDaiIntroduction = pointsDaiIntroduction;
    }

    public String getPointsDaiPresentationMission() {
        return pointsDaiPresentationMission;
    }

    public void setPointsDaiPresentationMission(
            String pointsDaiPresentationMission) {
        this.pointsDaiPresentationMission =
                pointsDaiPresentationMission;
    }

    public String getPointsDaiMethodologie() {
        return pointsDaiMethodologie;
    }

    public void setPointsDaiMethodologie(String pointsDaiMethodologie) {
        this.pointsDaiMethodologie = pointsDaiMethodologie;
    }

    public String getPointsInterlocuteursIntroduction() {
        return pointsInterlocuteursIntroduction;
    }

    public void setPointsInterlocuteursIntroduction(
            String pointsInterlocuteursIntroduction) {
        this.pointsInterlocuteursIntroduction =
                pointsInterlocuteursIntroduction;
    }

    public String getPointsInterlocuteursProcessus() {
        return pointsInterlocuteursProcessus;
    }

    public void setPointsInterlocuteursProcessus(
            String pointsInterlocuteursProcessus) {
        this.pointsInterlocuteursProcessus =
                pointsInterlocuteursProcessus;
    }

    public String getPointsInterlocuteursOrganisation() {
        return pointsInterlocuteursOrganisation;
    }

    public void setPointsInterlocuteursOrganisation(
            String pointsInterlocuteursOrganisation) {
        this.pointsInterlocuteursOrganisation =
                pointsInterlocuteursOrganisation;
    }

    public List<ReunionPersonneResponse> getParticipants() {
        return participants;
    }

    public void setParticipants(
            List<ReunionPersonneResponse> participants) {
        this.participants = participants;
    }
}