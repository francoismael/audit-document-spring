package com.audit.audit_document.application.dto;

import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Role;
import com.audit.audit_document.domain.entity.Utilisateur;

import java.time.LocalDateTime;

public class UtilisateurResponse {

    private Long id;
    private String username;
    private Role role;
    private Boolean actif;
    private LocalDateTime dateCreation;

    // Linked person of the directory, null when the account is not linked yet.
    private Long personneId;
    private String personneNom;
    private String personnePrenom;

    public static UtilisateurResponse from(Utilisateur utilisateur) {

        UtilisateurResponse response = new UtilisateurResponse();

        response.id = utilisateur.getId();
        response.username = utilisateur.getUsername();
        response.role = utilisateur.getRole();
        response.actif = utilisateur.getActif();
        response.dateCreation = utilisateur.getDateCreation();

        Personne personne = utilisateur.getPersonne();

        if (personne != null) {
            response.personneId = personne.getId();
            response.personneNom = personne.getNom();
            response.personnePrenom = personne.getPrenom();
        }

        return response;
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

    public Boolean getActif() {
        return actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Long getPersonneId() {
        return personneId;
    }

    public String getPersonneNom() {
        return personneNom;
    }

    public String getPersonnePrenom() {
        return personnePrenom;
    }
}
