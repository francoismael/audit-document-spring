package com.audit.audit_document.application.dto;

import java.time.LocalDate;

public class ReponseResponse {

    private Long id;

    private Long recommandationId;

    private String recommandationDescription;

    private String descriptions;

    private LocalDate dateReponse;

    public ReponseResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecommandationId() {
        return recommandationId;
    }

    public void setRecommandationId(Long recommandationId) {
        this.recommandationId = recommandationId;
    }

    public String getRecommandationDescription() {
        return recommandationDescription;
    }

    public void setRecommandationDescription(
            String recommandationDescription) {

        this.recommandationDescription =
                recommandationDescription;
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