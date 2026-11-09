package com.audit.audit_document.domain.exception;

/*
 * Thrown when the connected account may not reach a resource, such as a mission outside its team (HTTP 403).
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
