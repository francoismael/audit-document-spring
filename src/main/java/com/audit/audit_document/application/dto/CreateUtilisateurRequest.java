package com.audit.audit_document.application.dto;

import com.audit.audit_document.domain.entity.Role;

public class CreateUtilisateurRequest {

    private String username;
    private String password;
    private Role role;

    // Person of the directory linked to the account (optional).
    private Long personneId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Long personneId) {
        this.personneId = personneId;
    }
}
