package com.audit.audit_document.application.dto;

import java.util.List;

public class OrdreSectionsResponse {

    private String documentCode;

    // Section codes, from the top to the bottom of the document.
    private List<String> sections;

    public OrdreSectionsResponse() {
    }

    public OrdreSectionsResponse(String documentCode, List<String> sections) {
        this.documentCode = documentCode;
        this.sections = sections;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }
}
