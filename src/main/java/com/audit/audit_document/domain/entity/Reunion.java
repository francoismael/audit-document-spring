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

    @Column(length = 255)
    private String lieu;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "points_dai_introduction", columnDefinition = "TEXT")
    private String pointsDaiIntroduction;

    @Column(name = "points_dai_presentation_mission", columnDefinition = "TEXT")
    private String pointsDaiPresentationMission;

    @Column(name = "points_dai_methodologie", columnDefinition = "TEXT")
    private String pointsDaiMethodologie;

    @Column(name = "points_interlocuteurs_introduction", columnDefinition = "TEXT")
    private String pointsInterlocuteursIntroduction;

    @Column(name = "points_interlocuteurs_processus", columnDefinition = "TEXT")
    private String pointsInterlocuteursProcessus;

    @Column(name = "points_interlocuteurs_organisation", columnDefinition = "TEXT")
    private String pointsInterlocuteursOrganisation;

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

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPointsDaiPresentationMission(String pointsDaiPresentationMission) {
        this.pointsDaiPresentationMission = pointsDaiPresentationMission;
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

    public void setPointsInterlocuteursIntroduction(String pointsInterlocuteursIntroduction) {
        this.pointsInterlocuteursIntroduction = pointsInterlocuteursIntroduction;
    }

    public String getPointsInterlocuteursProcessus() {
        return pointsInterlocuteursProcessus;
    }

    public void setPointsInterlocuteursProcessus(String pointsInterlocuteursProcessus) {
        this.pointsInterlocuteursProcessus = pointsInterlocuteursProcessus;
    }

    public String getPointsInterlocuteursOrganisation() {
        return pointsInterlocuteursOrganisation;
    }

    public void setPointsInterlocuteursOrganisation(String pointsInterlocuteursOrganisation) {
        this.pointsInterlocuteursOrganisation = pointsInterlocuteursOrganisation;
    }

    public List<ReunionPersonne> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ReunionPersonne> participants) {
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