package com.audit.audit_document.application.dto;

public class RecommandationRequest {

    private Long id;

    private String description;

    private Boolean retenue;

    public RecommandationRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRetenue() {
        return retenue;
    }

    public void setRetenue(Boolean retenue) {
        this.retenue = retenue;
    }
}