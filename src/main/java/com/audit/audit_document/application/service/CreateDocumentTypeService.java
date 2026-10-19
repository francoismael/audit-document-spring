package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.application.dto.CreateDocumentTypeRequest;
import com.audit.audit_document.application.dto.DocumentTypeResponse;
import com.audit.audit_document.application.usecases.CreateDocumentTypeUseCase;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateDocumentTypeService
        implements CreateDocumentTypeUseCase {

    private final DocumentTypeRepository repository;

    public CreateDocumentTypeService(
            DocumentTypeRepository repository) {

        this.repository = repository;
    }

    @Override
    @Transactional
    public DocumentTypeResponse execute(
            CreateDocumentTypeRequest request) {

        if (request.getCode() == null
                || request.getCode().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le code du document est obligatoire");
        }

        if (request.getNom() == null
                || request.getNom().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le nom du document est obligatoire");
        }

        String code =
                request.getCode()
                        .trim()
                        .toUpperCase();

        if (repository.existsByCode(code)) {

            throw new ConflictException(
                    "Un type de document avec le code "
                            + code
                            + " existe déjà");
        }

        DocumentType documentType =
                new DocumentType();

        documentType.setCode(code);

        documentType.setNom(
                request.getNom().trim());

        documentType.setDescription(
                request.getDescription());

        documentType.setActif(true);

        documentType.setDateCreation(
                LocalDateTime.now());

        DocumentType saved =
                repository.save(documentType);

        return toResponse(saved);
    }

    private DocumentTypeResponse toResponse(
            DocumentType documentType) {

        DocumentTypeResponse response =
                new DocumentTypeResponse();

        response.setId(
                documentType.getId());

        response.setCode(
                documentType.getCode());

        response.setNom(
                documentType.getNom());

        response.setDescription(
                documentType.getDescription());

        response.setActif(
                documentType.getActif());

        response.setDateCreation(
                documentType.getDateCreation());

        return response;
    }
}