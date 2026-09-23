package com.audit.audit_document.application.dto;

public class EchantillonResponse {

    private Long id;

    private Integer numero;

    private String reference;

    private Boolean anomalieDetectee;

    private String observation;

    public EchantillonResponse() {
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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