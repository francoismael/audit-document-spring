package com.audit.audit_document.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "programme_travail",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_programme_mission",
            columnNames = "mission_id"
        )
    }
)
public class ProgrammeTravail {

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

    @OneToMany(
        mappedBy = "programme",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<LigneProgramme> lignes = new ArrayList<>();

    public ProgrammeTravail() {
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

    public List<LigneProgramme> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneProgramme> lignes) {
        this.lignes = lignes;
    }

    public void addLigne(LigneProgramme ligne) {
        lignes.add(ligne);
        ligne.setProgramme(this);
    }

    public void removeLigne(LigneProgramme ligne) {
        lignes.remove(ligne);
        ligne.setProgramme(null);
    }
}