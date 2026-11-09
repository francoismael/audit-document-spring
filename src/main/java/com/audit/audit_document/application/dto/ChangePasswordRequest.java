package com.audit.audit_document.application.dto;

// The connected account changes its own password.
public class ChangePasswordRequest {

    private String motDePasseActuel;
    private String nouveauMotDePasse;

    public String getMotDePasseActuel() {
        return motDePasseActuel;
    }

    public void setMotDePasseActuel(String motDePasseActuel) {
        this.motDePasseActuel = motDePasseActuel;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
}
