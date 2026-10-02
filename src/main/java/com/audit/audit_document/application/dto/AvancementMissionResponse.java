package com.audit.audit_document.application.dto;

public class AvancementMissionResponse {

    private Long missionId;

    private String missionNumero;

    private String missionIntitule;

    private int documentsCompletes;

    private int documentsAttendus;

    private double pourcentage;

    private boolean tdr;

    private boolean programme;

    private boolean tests;

    private boolean constats;

    private boolean rapport;

    private boolean pvAffirmation;

    private boolean pvCloture;

    public AvancementMissionResponse() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionNumero() {
        return missionNumero;
    }

    public void setMissionNumero(String missionNumero) {
        this.missionNumero = missionNumero;
    }

    public String getMissionIntitule() {
        return missionIntitule;
    }

    public void setMissionIntitule(String missionIntitule) {
        this.missionIntitule = missionIntitule;
    }

    public int getDocumentsCompletes() {
        return documentsCompletes;
    }

    public void setDocumentsCompletes(int documentsCompletes) {
        this.documentsCompletes = documentsCompletes;
    }

    public int getDocumentsAttendus() {
        return documentsAttendus;
    }

    public void setDocumentsAttendus(int documentsAttendus) {
        this.documentsAttendus = documentsAttendus;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public boolean isTdr() {
        return tdr;
    }

    public void setTdr(boolean tdr) {
        this.tdr = tdr;
    }

    public boolean isProgramme() {
        return programme;
    }

    public void setProgramme(boolean programme) {
        this.programme = programme;
    }

    public boolean isTests() {
        return tests;
    }

    public void setTests(boolean tests) {
        this.tests = tests;
    }

    public boolean isConstats() {
        return constats;
    }

    public void setConstats(boolean constats) {
        this.constats = constats;
    }

    public boolean isRapport() {
        return rapport;
    }

    public void setRapport(boolean rapport) {
        this.rapport = rapport;
    }

    public boolean isPvAffirmation() {
        return pvAffirmation;
    }

    public void setPvAffirmation(boolean pvAffirmation) {
        this.pvAffirmation = pvAffirmation;
    }

    public boolean isPvCloture() {
        return pvCloture;
    }

    public void setPvCloture(boolean pvCloture) {
        this.pvCloture = pvCloture;
    }
}