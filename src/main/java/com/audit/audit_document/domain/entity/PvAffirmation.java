package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "pv_affirmation",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_pv_affirmation_mission",
            columnNames = "mission_id"
        )
    }
)
public class PvAffirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "mission_id",
        nullable = false,
        unique = true
    )
    private Mission mission;

    @Column(
        name = "date_entretien",
        nullable = false
    )
    private LocalDate dateEntretien;

    @Column(name = "lieu")
    private String lieu;

    @Column(
        name = "observations_complementaires",
        columnDefinition = "TEXT"
    )
    private String observationsComplementaires;

    @OneToMany(
        mappedBy = "pvAffirmation",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<PvAffirmationParticipant> participants =
            new ArrayList<PvAffirmationParticipant>();

    @OneToMany(
        mappedBy = "pvAffirmation",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<PvAffirmationLigne> lignes =
            new ArrayList<PvAffirmationLigne>();

    public PvAffirmation() {
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

    public LocalDate getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(LocalDate dateEntretien) {
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

    public List<PvAffirmationParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(
            List<PvAffirmationParticipant> participants) {

        this.participants = participants;
    }

    public List<PvAffirmationLigne> getLignes() {
        return lignes;
    }

    public void setLignes(
            List<PvAffirmationLigne> lignes) {

        this.lignes = lignes;
    }

    public void addParticipant(
            PvAffirmationParticipant participant) {

        participant.setPvAffirmation(this);
        this.participants.add(participant);
    }

    public void addLigne(
            PvAffirmationLigne ligne) {

        ligne.setPvAffirmation(this);
        this.lignes.add(ligne);
    }
}