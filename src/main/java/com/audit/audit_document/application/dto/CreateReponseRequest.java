package com.audit.audit_document.application.dto;

import java.time.LocalDate;

public class CreateReponseRequest {

    private Long recommandationId;

    private String descriptions;

    private LocalDate dateReponse;

    public CreateReponseRequest() {
    }

    public Long getRecommandationId() {
        return recommandationId;
    }

    public void setRecommandationId(Long recommandationId) {
        this.recommandationId = recommandationId;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public LocalDate getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
    }
}