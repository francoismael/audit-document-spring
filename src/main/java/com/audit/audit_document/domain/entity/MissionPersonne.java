package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(
    name = "mission_personne",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_mission_personne_role",
            columnNames = {"mission_id", "personne_id", "roles"}
        )
    }
)
public class MissionPersonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    @Column(name = "roles", nullable = false, length = 100)
    private String roles;

    public MissionPersonne() {
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

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}