package com.audit.audit_document.application.dto;

public class GeneratedDocument {

    private final Long id;

    private final String fileName;

    private final byte[] content;

    public GeneratedDocument(
            Long id,
            String fileName,
            byte[] content) {

        this.id = id;
        this.fileName = fileName;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }
}
