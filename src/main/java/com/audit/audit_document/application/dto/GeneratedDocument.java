package com.audit.audit_document.application.dto;

public class GeneratedDocument {

    public static final String PDF_CONTENT_TYPE = "application/pdf";

    public static final String DOCX_CONTENT_TYPE =
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    private final Long id;

    private final String fileName;

    private final String contentType;

    private final byte[] content;

    public GeneratedDocument(
            Long id,
            String fileName,
            String contentType,
            byte[] content) {

        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
    }

    // Documents generated before the switch to PDF are still Word files.
    public static String contentTypeOf(String fileName) {

        return fileName != null && fileName.toLowerCase().endsWith(".docx")
                ? DOCX_CONTENT_TYPE
                : PDF_CONTENT_TYPE;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }
}
