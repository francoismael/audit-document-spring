package com.audit.audit_document.application.dto;

public class PvAffirmationParticipantResponse {

    private Long id;

    private Long missionPersonneId;

    private Long personneId;

    private String nom;

    private String prenom;

    private String fonction;

    private String roles;

    private String typeParticipant;

    public PvAffirmationParticipantResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMissionPersonneId() {
        return missionPersonneId;
    }

    public void setMissionPersonneId(
            Long missionPersonneId) {

        this.missionPersonneId =
                missionPersonneId;
    }

    public Long getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Long personneId) {
        this.personneId = personneId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getTypeParticipant() {
        return typeParticipant;
    }

    public void setTypeParticipant(
            String typeParticipant) {

        this.typeParticipant =
                typeParticipant;
    }
}