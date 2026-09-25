package com.audit.audit_document.application.dto;

public class RecommandationResponse {

    private Long id;

    private String description;

    private Boolean retenue;

    private Boolean maintenue;

    public RecommandationResponse() {
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

    public Boolean getMaintenue() {
        return maintenue;
    }

    public void setMaintenue(Boolean maintenue) {
        this.maintenue = maintenue;
    }
}