package com.audit.audit_document.application.dto;

import java.time.LocalDate;
import java.util.List;

public class CreateInterviewRequest {

    private Long missionId;

    private Long personneIntervieweeId;

    private LocalDate dateInterview;

    private String fonction;

    private String anciennete;

    private Long redigeParPersonneId;

    private Long superviseParPersonneId;

    private Long valideParPersonneId;

    private List<InterviewQuestionRequest> questions;

    public CreateInterviewRequest() {
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public Long getPersonneIntervieweeId() {
        return personneIntervieweeId;
    }

    public void setPersonneIntervieweeId(Long personneIntervieweeId) {
        this.personneIntervieweeId = personneIntervieweeId;
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

    public Long getSuperviseParPersonneId() {
        return superviseParPersonneId;
    }

    public void setSuperviseParPersonneId(Long superviseParPersonneId) {
        this.superviseParPersonneId = superviseParPersonneId;
    }

    public Long getValideParPersonneId() {
        return valideParPersonneId;
    }

    public void setValideParPersonneId(Long valideParPersonneId) {
        this.valideParPersonneId = valideParPersonneId;
    }

    public List<InterviewQuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<InterviewQuestionRequest> questions) {
        this.questions = questions;
    }
}