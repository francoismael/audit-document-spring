package com.audit.audit_document.application.dto;

import java.util.List;

public class ParametresResponse {

    private String nomApplication;
    private String dossierModeles;
    private String dossierDocumentsGeneres;
    private String tailleMaxFichier;
    private List<String> formatsModeles;
    private List<String> formatsGeneration;
    private String syntaxeVariables;
    private List<String> typesChamps;
    private List<String> rolesMission;

    public ParametresResponse() {
    }

    public String getNomApplication() {
        return nomApplication;
    }

    public void setNomApplication(String nomApplication) {
        this.nomApplication = nomApplication;
    }

    public String getDossierModeles() {
        return dossierModeles;
    }

    public void setDossierModeles(String dossierModeles) {
        this.dossierModeles = dossierModeles;
    }

    public String getDossierDocumentsGeneres() {
        return dossierDocumentsGeneres;
    }

    public void setDossierDocumentsGeneres(String dossierDocumentsGeneres) {
        this.dossierDocumentsGeneres = dossierDocumentsGeneres;
    }

    public String getTailleMaxFichier() {
        return tailleMaxFichier;
    }

    public void setTailleMaxFichier(String tailleMaxFichier) {
        this.tailleMaxFichier = tailleMaxFichier;
    }

    public List<String> getFormatsModeles() {
        return formatsModeles;
    }

    public void setFormatsModeles(List<String> formatsModeles) {
        this.formatsModeles = formatsModeles;
    }

    public List<String> getFormatsGeneration() {
        return formatsGeneration;
    }

    public void setFormatsGeneration(List<String> formatsGeneration) {
        this.formatsGeneration = formatsGeneration;
    }

    public String getSyntaxeVariables() {
        return syntaxeVariables;
    }

    public void setSyntaxeVariables(String syntaxeVariables) {
        this.syntaxeVariables = syntaxeVariables;
    }

    public List<String> getTypesChamps() {
        return typesChamps;
    }

    public void setTypesChamps(List<String> typesChamps) {
        this.typesChamps = typesChamps;
    }

    public List<String> getRolesMission() {
        return rolesMission;
    }

    public void setRolesMission(List<String> rolesMission) {
        this.rolesMission = rolesMission;
    }
}
