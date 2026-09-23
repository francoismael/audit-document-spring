package com.audit.audit_document.application.dto;

public class EchantillonRequest {

    private Long id;


    private Boolean anomalieDetectee;

    private String observation;

    public EchantillonRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAnomalieDetectee() {
        return anomalieDetectee;
    }

    public void setAnomalieDetectee(Boolean anomalieDetectee) {
        this.anomalieDetectee = anomalieDetectee;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}