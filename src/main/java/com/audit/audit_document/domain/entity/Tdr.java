package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "tdr",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_tdr_mission",
            columnNames = "mission_id"
        )
    }
)
public class Tdr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
        name = "mission_id",
        nullable = false,
        unique = true
    )
    private Mission mission;

    @Column(columnDefinition = "TEXT")
    private String contexte;

    @Column(columnDefinition = "TEXT")
    private String competence;

    @Column(columnDefinition = "TEXT")
    private String perimetre;

    @Column(name = "periode_observation", columnDefinition = "TEXT")
    private String periodeObservation;

    @Column(length = 255)
    private String lieu;

    @Column(name = "methodologie_travail", columnDefinition = "TEXT")
    private String methodologieTravail;

    @Column(name = "resultats_attendus", columnDefinition = "TEXT")
    private String resultatsAttendus;

    @Column(columnDefinition = "TEXT")
    private String livrables;

    @OneToMany(
        mappedBy = "tdr",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Objectif> objectifs = new ArrayList<>();

    public Tdr() {
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

    public String getContexte() {
        return contexte;
    }

    public void setContexte(String contexte) {
        this.contexte = contexte;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getPerimetre() {
        return perimetre;
    }

    public void setPerimetre(String perimetre) {
        this.perimetre = perimetre;
    }

    public String getPeriodeObservation() {
        return periodeObservation;
    }

    public void setPeriodeObservation(String periodeObservation) {
        this.periodeObservation = periodeObservation;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getMethodologieTravail() {
        return methodologieTravail;
    }

    public void setMethodologieTravail(String methodologieTravail) {
        this.methodologieTravail = methodologieTravail;
    }

    public String getResultatsAttendus() {
        return resultatsAttendus;
    }

    public void setResultatsAttendus(String resultatsAttendus) {
        this.resultatsAttendus = resultatsAttendus;
    }

    public String getLivrables() {
        return livrables;
    }

    public void setLivrables(String livrables) {
        this.livrables = livrables;
    }

    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<Objectif> objectifs) {
        this.objectifs = objectifs;
    }

    public void addObjectif(Objectif objectif) {
        objectifs.add(objectif);
        objectif.setTdr(this);
    }

    public void removeObjectif(Objectif objectif) {
        objectifs.remove(objectif);
        objectif.setTdr(null);
    }
}