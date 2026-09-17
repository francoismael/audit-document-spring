package com.audit.audit_document.application.dto;

public class ReunionPersonneRequest {

    private Long personneId;

    private String role;

    private String typeParticipant;

    public ReunionPersonneRequest() {
    }

    public Long getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Long personneId) {
        this.personneId = personneId;
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