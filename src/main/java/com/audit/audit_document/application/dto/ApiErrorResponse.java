package com.audit.audit_document.application.dto;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private final int status;

    private final String message;

    private final LocalDateTime timestamp;

    public ApiErrorResponse(int status, String message) {

        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
