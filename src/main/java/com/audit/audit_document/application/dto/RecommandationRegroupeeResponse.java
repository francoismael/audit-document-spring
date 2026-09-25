package com.audit.audit_document.application.dto;

import java.time.LocalDate;

public class RecommandationRegroupeeResponse {

    private Long constatId;

    private String constatReference;

    private String anomalie;

    private Long recommandationId;

    private String recommandation;

    private Long reponseId;

    private String reponse;

    private LocalDate dateReponse;

    private Boolean recommandationMaintenue;

    public RecommandationRegroupeeResponse() {
    }

    public Long getConstatId() {
        return constatId;
    }

    public void setConstatId(Long constatId) {
        this.constatId = constatId;
    }

    public String getConstatReference() {
        return constatReference;
    }

    public void setConstatReference(String constatReference) {
        this.constatReference = constatReference;
    }

    public String getAnomalie() {
        return anomalie;
    }

    public void setAnomalie(String anomalie) {
        this.anomalie = anomalie;
    }

    public Long getRecommandationId() {
        return recommandationId;
    }

    public void setRecommandationId(Long recommandationId) {
        this.recommandationId = recommandationId;
    }

    public String getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(String recommandation) {
        this.recommandation = recommandation;
    }

    public Long getReponseId() {
        return reponseId;
    }

    public void setReponseId(Long reponseId) {
        this.reponseId = reponseId;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public LocalDate getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
    }

    public Boolean getRecommandationMaintenue() {
        return recommandationMaintenue;
    }

    public void setRecommandationMaintenue(
            Boolean recommandationMaintenue) {

        this.recommandationMaintenue =
                recommandationMaintenue;
    }
}