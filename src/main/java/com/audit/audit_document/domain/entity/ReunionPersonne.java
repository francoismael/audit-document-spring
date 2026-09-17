package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "reunion_personne")
public class ReunionPersonne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reunion_id", nullable = false)
    private Reunion reunion;

    @ManyToOne
    @JoinColumn(name = "personne_id", nullable = false)
    private Personne personne;

    @Column(length = 150)
    private String role;

    @Column(name = "type_participant", length = 100)
    private String typeParticipant;

    public ReunionPersonne() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTypeParticipant() {
        return typeParticipant;
    }

    public void setTypeParticipant(String typeParticipant) {
        this.typeParticipant = typeParticipant;
    }
}