package com.audit.audit_document.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "pv_affirmation_participant")
public class PvAffirmationParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "pv_affirmation_id",
        nullable = false
    )
    private PvAffirmation pvAffirmation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "mission_personne_id",
        nullable = false
    )
    private MissionPersonne missionPersonne;

    @Column(
        name = "type_participant",
        nullable = false
    )
    private String typeParticipant;

    public PvAffirmationParticipant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PvAffirmation getPvAffirmation() {
        return pvAffirmation;
    }

    public void setPvAffirmation(
            PvAffirmation pvAffirmation) {

        this.pvAffirmation = pvAffirmation;
    }

    public MissionPersonne getMissionPersonne() {
        return missionPersonne;
    }

    public void setMissionPersonne(
            MissionPersonne missionPersonne) {

        this.missionPersonne = missionPersonne;
    }

    public String getTypeParticipant() {
        return typeParticipant;
    }

    public void setTypeParticipant(
            String typeParticipant) {

        this.typeParticipant = typeParticipant;
    }
}