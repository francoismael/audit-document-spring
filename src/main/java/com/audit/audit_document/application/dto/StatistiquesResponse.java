package com.audit.audit_document.application.dto;

import java.util.List;

public class StatistiquesResponse {

    private long nombreMissions;
    private long missionsEnCours;
    private long missionsAVenir;
    private long missionsTerminees;
    private long missionsSansDates;

    private long nombreTypesDocuments;
    private long nombreTypesActifs;

    private long nombreDocumentsGeneres;

    private List<TypeDocumentStatistiqueResponse> documentsParType;
    private List<MoisStatistiqueResponse> documentsParMois;
    private List<DocumentGenereResponse> derniersDocuments;

    public StatistiquesResponse() {
    }

    public long getNombreMissions() {
        return nombreMissions;
    }

    public void setNombreMissions(long nombreMissions) {
        this.nombreMissions = nombreMissions;
    }

    public long getMissionsEnCours() {
        return missionsEnCours;
    }

    public void setMissionsEnCours(long missionsEnCours) {
        this.missionsEnCours = missionsEnCours;
    }

    public long getMissionsAVenir() {
        return missionsAVenir;
    }

    public void setMissionsAVenir(long missionsAVenir) {
        this.missionsAVenir = missionsAVenir;
    }

    public long getMissionsTerminees() {
        return missionsTerminees;
    }

    public void setMissionsTerminees(long missionsTerminees) {
        this.missionsTerminees = missionsTerminees;
    }

    public long getMissionsSansDates() {
        return missionsSansDates;
    }

    public void setMissionsSansDates(long missionsSansDates) {
        this.missionsSansDates = missionsSansDates;
    }

    public long getNombreTypesDocuments() {
        return nombreTypesDocuments;
    }

    public void setNombreTypesDocuments(long nombreTypesDocuments) {
        this.nombreTypesDocuments = nombreTypesDocuments;
    }

    public long getNombreTypesActifs() {
        return nombreTypesActifs;
    }

    public void setNombreTypesActifs(long nombreTypesActifs) {
        this.nombreTypesActifs = nombreTypesActifs;
    }

    public long getNombreDocumentsGeneres() {
        return nombreDocumentsGeneres;
    }

    public void setNombreDocumentsGeneres(long nombreDocumentsGeneres) {
        this.nombreDocumentsGeneres = nombreDocumentsGeneres;
    }

    public List<TypeDocumentStatistiqueResponse> getDocumentsParType() {
        return documentsParType;
    }

    public void setDocumentsParType(
            List<TypeDocumentStatistiqueResponse> documentsParType) {

        this.documentsParType = documentsParType;
    }

    public List<MoisStatistiqueResponse> getDocumentsParMois() {
        return documentsParMois;
    }

    public void setDocumentsParMois(
            List<MoisStatistiqueResponse> documentsParMois) {

        this.documentsParMois = documentsParMois;
    }

    public List<DocumentGenereResponse> getDerniersDocuments() {
        return derniersDocuments;
    }

    public void setDerniersDocuments(
            List<DocumentGenereResponse> derniersDocuments) {

        this.derniersDocuments = derniersDocuments;
    }
}
