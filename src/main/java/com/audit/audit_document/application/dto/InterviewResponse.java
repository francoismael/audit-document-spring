package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.List;

public class InterviewResponse {

    private Long id;

    private Long missionId;
    private String missionNumero;
    private String missionIntitule;

    private Long personneIntervieweeId;
    private String personneIntervieweeNom;
    private String personneIntervieweePrenom;

    private String reference;
    private LocalDate dateInterview;
    private String fonction;
    private String anciennete;

    private Long redigeParPersonneId;
    private String redigeParPersonneNom;
    private String redigeParPersonnePrenom;

    private Long superviseParPersonneId;
    private String superviseParPersonneNom;
    private String superviseParPersonnePrenom;

    private Long valideParPersonneId;
    private String valideParPersonneNom;
    private String valideParPersonnePrenom;

    private List<InterviewQuestionResponse> questions;

    public InterviewResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPersonneIntervieweeId() {
        return personneIntervieweeId;
    }

    public void setPersonneIntervieweeId(Long personneIntervieweeId) {
        this.personneIntervieweeId = personneIntervieweeId;
    }

    public String getPersonneIntervieweeNom() {
        return personneIntervieweeNom;
    }

    public void setPersonneIntervieweeNom(String personneIntervieweeNom) {
        this.personneIntervieweeNom = personneIntervieweeNom;
    }

    public String getPersonneIntervieweePrenom() {
        return personneIntervieweePrenom;
    }

    public void setPersonneIntervieweePrenom(String personneIntervieweePrenom) {
        this.personneIntervieweePrenom = personneIntervieweePrenom;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateInterview() {
        return dateInterview;
    }

    public void setDateInterview(LocalDate dateInterview) {
        this.dateInterview = dateInterview;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(String anciennete) {
        this.anciennete = anciennete;
    }

    public Long getRedigeParPersonneId() {
        return redigeParPersonneId;
    }

    public void setRedigeParPersonneId(Long redigeParPersonneId) {
        this.redigeParPersonneId = redigeParPersonneId;
    }

    public String getRedigeParPersonneNom() {
        return redigeParPersonneNom;
    }

    public void setRedigeParPersonneNom(String redigeParPersonneNom) {
        this.redigeParPersonneNom = redigeParPersonneNom;
    }

    public String getRedigeParPersonnePrenom() {
        return redigeParPersonnePrenom;
    }

    public void setRedigeParPersonnePrenom(String redigeParPersonnePrenom) {
        this.redigeParPersonnePrenom = redigeParPersonnePrenom;
    }

    public Long getSuperviseParPersonneId() {
        return superviseParPersonneId;
    }

    public void setSuperviseParPersonneId(Long superviseParPersonneId) {
        this.superviseParPersonneId = superviseParPersonneId;
    }

    public String getSuperviseParPersonneNom() {
        return superviseParPersonneNom;
    }

    public void setSuperviseParPersonneNom(String superviseParPersonneNom) {
        this.superviseParPersonneNom = superviseParPersonneNom;
    }

    public String getSuperviseParPersonnePrenom() {
        return superviseParPersonnePrenom;
    }

    public void setSuperviseParPersonnePrenom(String superviseParPersonnePrenom) {
        this.superviseParPersonnePrenom = superviseParPersonnePrenom;
    }

    public Long getValideParPersonneId() {
        return valideParPersonneId;
    }

    public void setValideParPersonneId(Long valideParPersonneId) {
        this.valideParPersonneId = valideParPersonneId;
    }

    public String getValideParPersonneNom() {
        return valideParPersonneNom;
    }

    public void setValideParPersonneNom(String valideParPersonneNom) {
        this.valideParPersonneNom = valideParPersonneNom;
    }

    public String getValideParPersonnePrenom() {
        return valideParPersonnePrenom;
    }

    public void setValideParPersonnePrenom(String valideParPersonnePrenom) {
        this.valideParPersonnePrenom = valideParPersonnePrenom;
    }

    public List<InterviewQuestionResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<InterviewQuestionResponse> questions) {
        this.questions = questions;
    }
}