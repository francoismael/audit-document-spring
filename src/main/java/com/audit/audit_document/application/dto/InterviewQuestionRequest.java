package com.audit.audit_document.application.dto;

public class InterviewQuestionRequest {

    private Integer numero;
    private String question;
    private String reponse;

    public InterviewQuestionRequest() {
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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