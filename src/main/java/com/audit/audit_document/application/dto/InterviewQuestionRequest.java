package com.audit.audit_document.application.dto;

public class InterviewQuestionRequest {

    private String question;
    private String reponse;

    public InterviewQuestionRequest() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
}