package com.audit.audit_document.application.dto;

import java.util.List;

public class OrdreSectionsRequest {

    // Section codes, from the top to the bottom of the document.
    private List<String> sections;

    public OrdreSectionsRequest() {
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }
}
