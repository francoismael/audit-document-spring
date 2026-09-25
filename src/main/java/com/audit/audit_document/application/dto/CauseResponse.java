package com.audit.audit_document.application.dto;

public class CauseResponse {

    private Long id;

    private String descriptions;

    public CauseResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}