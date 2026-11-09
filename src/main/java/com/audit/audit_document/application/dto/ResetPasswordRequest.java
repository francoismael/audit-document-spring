package com.audit.audit_document.application.dto;

// An administrator gives a new password to an account.
public class ResetPasswordRequest {

    private String motDePasse;

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
