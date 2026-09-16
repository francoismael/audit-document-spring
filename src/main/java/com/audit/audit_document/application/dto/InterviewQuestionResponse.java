package com.audit.audit_document.application.dto;

public class InterviewQuestionResponse {

    private Long id;
    private Integer numero;
    private String question;
    private String reponse;

    public InterviewQuestionResponse() {
    }

    public InterviewQuestionResponse(
            Long id,
            Integer numero,
            String question,
            String reponse) {

        this.id = id;
        this.numero = numero;
        this.question = question;
        this.reponse = reponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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