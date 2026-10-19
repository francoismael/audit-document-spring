package com.audit.audit_document.domain.exception;

/*
 * Thrown when an operation conflicts with existing data, such as a duplicate (HTTP 409).
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
