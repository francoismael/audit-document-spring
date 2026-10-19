package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.application.dto.CreateDocumentTypeRequest;
import com.audit.audit_document.application.dto.DocumentTypeResponse;
import com.audit.audit_document.application.usecases.ChangeDocumentTypeStatusUseCase;
import com.audit.audit_document.application.usecases.DeleteDocumentTypeUseCase;
import com.audit.audit_document.application.usecases.UpdateDocumentTypeUseCase;
import com.audit.audit_document.domain.entity.DocumentField;
import com.audit.audit_document.domain.entity.DocumentTemplate;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentFieldRepository;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentTypeManagementService
        implements UpdateDocumentTypeUseCase,
                   ChangeDocumentTypeStatusUseCase,
                   DeleteDocumentTypeUseCase {

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentFieldRepository documentFieldRepository;

    private final DocumentGenereRepository documentGenereRepository;

    private final DocumentStorage documentStorage;

    public DocumentTypeManagementService(
            DocumentTypeRepository documentTypeRepository,
            DocumentFieldRepository documentFieldRepository,
            DocumentGenereRepository documentGenereRepository,
            DocumentStorage documentStorage) {

        this.documentTypeRepository =
                documentTypeRepository;

        this.documentFieldRepository =
                documentFieldRepository;

        this.documentGenereRepository =
                documentGenereRepository;

        this.documentStorage =
                documentStorage;
    }

    @Override
    @Transactional
    public DocumentTypeResponse update(
            Long id,
            CreateDocumentTypeRequest request) {

        DocumentType documentType = findDocumentType(id);

        if (request == null
                || request.getCode() == null
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

        if (documentTypeRepository.existsByCodeAndIdNot(code, id)) {

            throw new ConflictException(
                    "Un type de document avec le code "
                            + code
                            + " existe déjà");
        }

        documentType.setCode(code);
        documentType.setNom(request.getNom().trim());
        documentType.setDescription(request.getDescription());

        return toResponse(
                documentTypeRepository.save(documentType));
    }

    @Override
    @Transactional
    public DocumentTypeResponse changeStatus(
            Long id,
            Boolean actif) {

        if (actif == null) {

            throw new IllegalArgumentException(
                    "Le statut actif est obligatoire");
        }

        DocumentType documentType = findDocumentType(id);

        documentType.setActif(actif);

        return toResponse(
                documentTypeRepository.save(documentType));
    }

    /*
     * A type already used to generate documents is kept
     * so the history is not broken: it must be deactivated instead.
     */
    @Override
    @Transactional
    public void delete(Long id) {

        DocumentType documentType = findDocumentType(id);

        long generatedCount =
                documentGenereRepository.countByDocumentTypeId(id);

        if (generatedCount > 0) {

            throw new ConflictException(
                    "Ce type a déjà servi à générer "
                            + generatedCount
                            + " document(s). Désactivez-le plutôt "
                            + "que de le supprimer.");
        }

        List<Path> templateFiles = new ArrayList<>();

        for (DocumentTemplate template : documentType.getTemplates()) {

            templateFiles.add(documentStorage.templatePath(template));
        }

        for (DocumentField field
                : documentFieldRepository.findByDocumentTypeId(id)) {

            documentFieldRepository.deleteById(field.getId());
        }

        documentTypeRepository.deleteById(id);

        for (Path file : templateFiles) {

            documentStorage.deleteQuietly(file);
        }
    }

    private DocumentType findDocumentType(Long id) {

        return documentTypeRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Type de document introuvable"));
    }

    private DocumentTypeResponse toResponse(
            DocumentType documentType) {

        DocumentTypeResponse response =
                new DocumentTypeResponse();

        response.setId(documentType.getId());
        response.setCode(documentType.getCode());
        response.setNom(documentType.getNom());
        response.setDescription(documentType.getDescription());
        response.setActif(documentType.getActif());
        response.setDateCreation(documentType.getDateCreation());

        return response;
    }
}
