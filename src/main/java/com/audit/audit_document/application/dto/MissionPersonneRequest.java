package com.audit.audit_document.application.dto;

public class MissionPersonneRequest {

    private Long personneId;
    private String roles;

    public MissionPersonneRequest() {
    }

    public Long getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Long personneId) {
        this.personneId = personneId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}