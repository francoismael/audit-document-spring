package com.audit.audit_document.application.dto;

import com.audit.audit_document.domain.entity.Role;

public class LoginResponse {

    private Long id;
    private String username;
    private Role role;
    private String token;

    public LoginResponse(
            Long id,
            String username,
            Role role,
            String token
    ) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}