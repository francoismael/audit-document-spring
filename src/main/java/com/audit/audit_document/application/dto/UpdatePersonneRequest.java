package com.audit.audit_document.application.dto;

import com.audit.audit_document.domain.entity.Sexe;

public class UpdatePersonneRequest {

    private String nom;
    private String prenom;
    private String fonction;
    private String services;
    private Sexe sexe;

    public UpdatePersonneRequest() {
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

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }
}
