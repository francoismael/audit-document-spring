package com.audit.audit_document.application.dto;

public class MoisStatistiqueResponse {

    private String mois;
    private long nombre;

    public MoisStatistiqueResponse() {
    }

    public MoisStatistiqueResponse(String mois, long nombre) {

        this.mois = mois;
        this.nombre = nombre;
    }

    public String getMois() {
        return mois;
    }

    public long getNombre() {
        return nombre;
    }
}
