package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reunion")
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(nullable = false, length = 30)
    private String type;

    @Column(name = "date_reunion", nullable = false)
    private LocalDate dateReunion;

    @Column(name = "heure_debut")
    private LocalTime heureDebut;

    @Column(name = "heure_fin")
    private LocalTime heureFin;

    @Column(name = "heure_levee")
    private LocalTime heureLevee;

    @Column(length = 255)
    private String lieu;

    /* =========================
       REUNION D'OUVERTURE
       ========================= */

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(
        name = "points_dai_introduction",
        columnDefinition = "TEXT"
    )
    private String pointsDaiIntroduction;

    @Column(
        name = "points_dai_presentation_mission",
        columnDefinition = "TEXT"
    )
    private String pointsDaiPresentationMission;

    @Column(
        name = "points_dai_methodologie",
        columnDefinition = "TEXT"
    )
    private String pointsDaiMethodologie;

    @Column(
        name = "points_interlocuteurs_introduction",
        columnDefinition = "TEXT"
    )
    private String pointsInterlocuteursIntroduction;

    @Column(
        name = "points_interlocuteurs_processus",
        columnDefinition = "TEXT"
    )
    private String pointsInterlocuteursProcessus;

    @Column(
        name = "points_interlocuteurs_organisation",
        columnDefinition = "TEXT"
    )
    private String pointsInterlocuteursOrganisation;

    /* =========================
       REUNION DE CLOTURE
       ========================= */

    @Column(
        name = "remerciements",
        columnDefinition = "TEXT"
    )
    private String remerciements;

    @Column(
        name = "rappel_perimetre",
        columnDefinition = "TEXT"
    )
    private String rappelPerimetre;

    @Column(
        name = "synthese_constats",
        columnDefinition = "TEXT"
    )
    private String syntheseConstats;

    @Column(
        name = "points_forts_identifies",
        columnDefinition = "TEXT"
    )
    private String pointsFortsIdentifies;

    @Column(
        name = "resume_resultats_verification",
        columnDefinition = "TEXT"
    )
    private String resumeResultatsVerification;

    @Column(
        name = "resume_recommandations_plans_actions",
        columnDefinition = "TEXT"
    )
    private String resumeRecommandationsPlansActions;

    @Column(
        name = "observations_commentaires",
        columnDefinition = "TEXT"
    )
    private String observationsCommentaires;

    /* =========================
       PARTICIPANTS
       ========================= */

    @OneToMany(
        mappedBy = "reunion",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ReunionPersonne> participants = new ArrayList<>();

    public Reunion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
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

    public void setPointsFortsIdentifies(String pointsFortsIdentifies) {
        this.pointsFortsIdentifies = pointsFortsIdentifies;
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

    public List<ReunionPersonne> getParticipants() {
        return participants;
    }

    public void setParticipants(
            List<ReunionPersonne> participants) {
        this.participants = participants;
    }

    public void addParticipant(ReunionPersonne participant) {
        participants.add(participant);
        participant.setReunion(this);
    }

    public void removeParticipant(ReunionPersonne participant) {
        participants.remove(participant);
        participant.setReunion(null);
    }
}