package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.GeneratedDocument;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;

final class FileResponses {

    static final String DOCUMENT_ID_HEADER = "X-Document-Id";

    private FileResponses() {
    }

    static ResponseEntity<byte[]> attachment(GeneratedDocument document) {

        ContentDisposition contentDisposition =
                ContentDisposition.attachment()
                        .filename(
                                document.getFileName(),
                                StandardCharsets.UTF_8)
                        .build();

        ResponseEntity.BodyBuilder builder =
                ResponseEntity.ok()
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                contentDisposition.toString())
                        .contentType(MediaType.parseMediaType(document.getContentType()));

        if (document.getId() != null) {

            builder.header(
                    DOCUMENT_ID_HEADER,
                    String.valueOf(document.getId()));
        }

        return builder.body(document.getContent());
    }
}
