package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CreateReunionRequest {

    private Long missionId;

    private String type;

    private LocalDate dateReunion;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private LocalTime heureLevee;

    private String lieu;

    /* reunion ouverture */

    private String observations;

    private String pointsDaiIntroduction;

    private String pointsDaiPresentationMission;

    private String pointsDaiMethodologie;

    private String pointsInterlocuteursIntroduction;

    private String pointsInterlocuteursProcessus;

    private String pointsInterlocuteursOrganisation;

    /* reunion cloture */

    private String remerciements;

    private String rappelPerimetre;

    private String syntheseConstats;

    private String pointsFortsIdentifies;

    private String resumeResultatsVerification;

    private String resumeRecommandationsPlansActions;

    private String observationsCommentaires;

    private List<ReunionPersonneRequest> participants;

    public CreateReunionRequest() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
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

    public LocalTime getHeureLevee() {
        return heureLevee;
    }

    public void setHeureLevee(LocalTime heureLevee) {
        this.heureLevee = heureLevee;
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

    public void setPointsDaiIntroduction(
            String pointsDaiIntroduction) {
        this.pointsDaiIntroduction =
                pointsDaiIntroduction;
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

    public void setPointsDaiMethodologie(
            String pointsDaiMethodologie) {
        this.pointsDaiMethodologie =
                pointsDaiMethodologie;
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

    public String getRemerciements() {
        return remerciements;
    }

    public void setRemerciements(String remerciements) {
        this.remerciements = remerciements;
    }

    public String getRappelPerimetre() {
        return rappelPerimetre;
    }

    public void setRappelPerimetre(String rappelPerimetre) {
        this.rappelPerimetre = rappelPerimetre;
    }

    public String getSyntheseConstats() {
        return syntheseConstats;
    }

    public void setSyntheseConstats(String syntheseConstats) {
        this.syntheseConstats = syntheseConstats;
    }

    public String getPointsFortsIdentifies() {
        return pointsFortsIdentifies;
    }

    public void setPointsFortsIdentifies(
            String pointsFortsIdentifies) {
        this.pointsFortsIdentifies =
                pointsFortsIdentifies;
    }

    public String getResumeResultatsVerification() {
        return resumeResultatsVerification;
    }

    public void setResumeResultatsVerification(
            String resumeResultatsVerification) {
        this.resumeResultatsVerification =
                resumeResultatsVerification;
    }

    public String getResumeRecommandationsPlansActions() {
        return resumeRecommandationsPlansActions;
    }

    public void setResumeRecommandationsPlansActions(
            String resumeRecommandationsPlansActions) {
        this.resumeRecommandationsPlansActions =
                resumeRecommandationsPlansActions;
    }

    public String getObservationsCommentaires() {
        return observationsCommentaires;
    }

    public void setObservationsCommentaires(
            String observationsCommentaires) {
        this.observationsCommentaires =
                observationsCommentaires;
    }

    public List<ReunionPersonneRequest> getParticipants() {
        return participants;
    }

    public void setParticipants(
            List<ReunionPersonneRequest> participants) {
        this.participants = participants;
    }
}