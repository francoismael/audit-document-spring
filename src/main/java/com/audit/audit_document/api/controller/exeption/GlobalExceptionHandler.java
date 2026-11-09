package com.audit.audit_document.api.controller.exeption;

import com.audit.audit_document.application.dto.ApiErrorResponse;
import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.domain.exception.ForbiddenException;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException exception) {

        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(
            ConflictException exception) {

        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleForbidden(
            ForbiddenException exception) {

        return buildResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleBadRequest(
            IllegalArgumentException exception) {

        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthentication(
            AuthenticationException exception) {

        return buildResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(
            AccessDeniedException exception) {

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "Accès refusé : vous n'avez pas les droits nécessaires");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableBody(
            HttpMessageNotReadableException exception) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Le corps de la requête est invalide ou mal formé.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Valeur invalide pour le paramètre « " + exception.getName() + " ».");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Le paramètre « " + exception.getParameterName() + " » est obligatoire.");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleFileTooLarge(
            MaxUploadSizeExceededException exception) {

        return buildResponse(
                HttpStatus.PAYLOAD_TOO_LARGE,
                "Le fichier dépasse la taille maximale autorisée.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException exception) {

        LOGGER.warn("Data integrity violation", exception);

        return buildResponse(
                HttpStatus.CONFLICT,
                "Opération impossible : cet élément est lié à d'autres données.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
            RuntimeException exception) {

        LOGGER.error("Unexpected error", exception);

        String message =
                exception.getMessage() != null
                        ? exception.getMessage()
                        : "Une erreur interne est survenue.";

        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    /*
     * The content type is forced to JSON so the error stays readable
     * even when the request expected a DOCX file.
     */
    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String message) {

        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiErrorResponse(status.value(), message));
    }
}
