package com.audit.audit_document.application.dto;

import com.audit.audit_document.domain.entity.Role;

import java.time.LocalDateTime;

public class UtilisateurResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private Boolean actif;
    private LocalDateTime dateCreation;

    public UtilisateurResponse(
            Long id,
            String username,
            String email,
            Role role,
            Boolean actif,
            LocalDateTime dateCreation
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.actif = actif;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Boolean getActif() {
        return actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }
}