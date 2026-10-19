package com.audit.audit_document.application.dto;

public class TypeDocumentStatistiqueResponse {

    private Long documentTypeId;
    private String code;
    private String nom;
    private long nombre;

    public TypeDocumentStatistiqueResponse() {
    }

    public TypeDocumentStatistiqueResponse(
            Long documentTypeId,
            String code,
            String nom,
            long nombre) {

        this.documentTypeId = documentTypeId;
        this.code = code;
        this.nom = nom;
        this.nombre = nombre;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public long getNombre() {
        return nombre;
    }
}
