package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.CreateDocumentFieldRequest;
import com.audit.audit_document.application.dto.CreateDocumentFieldsRequest;
import com.audit.audit_document.application.dto.DocumentFieldItemRequest;
import com.audit.audit_document.application.dto.DocumentFieldResponse;
import com.audit.audit_document.application.usecases.CreateDocumentFieldUseCase;
import com.audit.audit_document.application.usecases.CreateDocumentFieldsUseCase;
import com.audit.audit_document.application.usecases.DeleteDocumentFieldUseCase;
import com.audit.audit_document.application.usecases.GetDocumentFieldByIdUseCase;
import com.audit.audit_document.application.usecases.GetDocumentFieldsUseCase;
import com.audit.audit_document.application.usecases.UpdateDocumentFieldUseCase;
import com.audit.audit_document.domain.entity.DocumentField;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentFieldRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentFieldService
        implements CreateDocumentFieldUseCase,
        CreateDocumentFieldsUseCase,
        GetDocumentFieldsUseCase,
        GetDocumentFieldByIdUseCase,
        UpdateDocumentFieldUseCase,
        DeleteDocumentFieldUseCase {

    private final DocumentFieldRepository documentFieldRepository;

    private final DocumentTypeRepository documentTypeRepository;

    public static final Set<String> TYPES_AUTORISES =
            new HashSet<>(
                    Arrays.asList(
                            "TEXT",
                            "TEXTAREA",
                            "DATE",
                            "NUMBER",
                            "BOOLEAN"
                    )
            );

    public DocumentFieldService(
            DocumentFieldRepository documentFieldRepository,
            DocumentTypeRepository documentTypeRepository) {

        this.documentFieldRepository =
                documentFieldRepository;

        this.documentTypeRepository =
                documentTypeRepository;
    }

    /*
     * ============================================================
     * CREATE A SINGLE FIELD
     * ============================================================
     */

    @Override
    @Transactional
    public DocumentFieldResponse create(
            CreateDocumentFieldRequest request) {

        validateRequest(request);

        DocumentType documentType =
                documentTypeRepository
                        .findById(request.getDocumentTypeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Type de document introuvable"));

        DocumentField documentField =
                new DocumentField();

        documentField.setDocumentType(
                documentType);

        documentField.setNom(
                request.getNom());

        documentField.setLibelle(
                request.getLibelle());

        documentField.setType(
                request.getType());

        documentField.setObligatoire(
                request.getObligatoire() != null
                        ? request.getObligatoire()
                        : false
        );

        /*
         * Order is generated automatically.
         */
        documentField.setOrdre(
                getNextOrder(
                        request.getDocumentTypeId()));

        DocumentField saved =
                documentFieldRepository.save(
                        documentField);

        return toResponse(saved);
    }



    @Override
    @Transactional
    public List<DocumentFieldResponse> create(
            CreateDocumentFieldsRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "La requête est obligatoire");
        }

        if (request.getDocumentTypeId() == null) {

            throw new IllegalArgumentException(
                    "documentTypeId est obligatoire");
        }

        if (request.getFields() == null
                || request.getFields().isEmpty()) {

            throw new IllegalArgumentException(
                    "La liste des champs est obligatoire");
        }

        DocumentType documentType =
                documentTypeRepository
                        .findById(
                                request.getDocumentTypeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Type de document introuvable"));

        /*
         * Start after the last existing field.
         */
        int nextOrder =
                getNextOrder(
                        request.getDocumentTypeId());

        List<DocumentField> documentFields =
                new ArrayList<>();

        /*
         * The order follows the order of the list
         * received from the client.
         */
        for (DocumentFieldItemRequest fieldRequest
                : request.getFields()) {

            validateItemRequest(fieldRequest);

            DocumentField documentField =
                    new DocumentField();

            documentField.setDocumentType(
                    documentType);

            documentField.setNom(
                    fieldRequest.getNom()
                            .trim());

            documentField.setLibelle(
                    fieldRequest.getLibelle()
                            .trim());

            documentField.setType(
                    fieldRequest.getType()
                            .trim()
                            .toUpperCase());

            documentField.setObligatoire(
                    fieldRequest.getObligatoire() != null
                            ? fieldRequest.getObligatoire()
                            : false
            );

            /*
             * Automatic order.
             */
            documentField.setOrdre(
                    nextOrder);

            documentFields.add(
                    documentField);

            nextOrder++;
        }

        List<DocumentField> savedFields =
                documentFields
                        .stream()
                        .map(documentFieldRepository::save)
                        .collect(Collectors.toList());

        return savedFields
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /*
     * ============================================================
     * GET BY DOCUMENT TYPE
     * ============================================================
     */

    @Override
    @Transactional(readOnly = true)
    public List<DocumentFieldResponse> getByDocumentType(
            Long documentTypeId) {

        if (documentTypeId == null) {

            throw new IllegalArgumentException(
                    "L'identifiant du type de document est obligatoire");
        }

        if (!documentTypeRepository
                .findById(documentTypeId)
                .isPresent()) {

            throw new ResourceNotFoundException(
                    "Type de document introuvable");
        }

        List<DocumentField> fields =
                documentFieldRepository
                        .findByDocumentTypeId(
                                documentTypeId);

        /*
         * Always return fields in the correct order.
         */
        fields.sort(
                Comparator.comparing(
                        DocumentField::getOrdre));

        return fields
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional(readOnly = true)
    public DocumentFieldResponse getById(
            Long id) {

        if (id == null) {

            throw new IllegalArgumentException(
                    "L'identifiant du champ est obligatoire");
        }

        DocumentField documentField =
                documentFieldRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Champ du document introuvable"));

        return toResponse(documentField);
    }



    @Override
    @Transactional
    public DocumentFieldResponse update(
            Long id,
            CreateDocumentFieldRequest request) {

        if (id == null) {

            throw new IllegalArgumentException(
                    "L'identifiant du champ est obligatoire");
        }

        validateRequest(request);

        DocumentField documentField =
                documentFieldRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Champ du document introuvable"));

        Long oldDocumentTypeId =
                documentField
                        .getDocumentType()
                        .getId();

        DocumentType newDocumentType =
                documentTypeRepository
                        .findById(
                                request.getDocumentTypeId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Type de document introuvable"));

        /*
         * Check whether the field is moved to another
         * document type.
         */
        boolean documentTypeChanged =
                !oldDocumentTypeId.equals(
                        request.getDocumentTypeId());

        documentField.setDocumentType(
                newDocumentType);

        documentField.setNom(
                request.getNom());

        documentField.setLibelle(
                request.getLibelle());

        documentField.setType(
                request.getType());

        documentField.setObligatoire(
                request.getObligatoire() != null
                        ? request.getObligatoire()
                        : false
        );

        /*
         * If the document type changes,
         * assign the next order in the new type.
         *
         * Otherwise keep the current order.
         */
        if (documentTypeChanged) {

            documentField.setOrdre(
                    getNextOrder(
                            request.getDocumentTypeId()));
        }

        DocumentField updated =
                documentFieldRepository.save(
                        documentField);

        /*
         * If the field was moved to another document type,
         * reorganize the old document type.
         */
        if (documentTypeChanged) {

            reorganizeOrders(
                    oldDocumentTypeId);
        }

        return toResponse(updated);
    }



    @Override
    @Transactional
    public void delete(
            Long id) {

        if (id == null) {

            throw new IllegalArgumentException(
                    "L'identifiant du champ est obligatoire");
        }

        DocumentField documentField =
                documentFieldRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Champ du document introuvable"));

        Long documentTypeId =
                documentField
                        .getDocumentType()
                        .getId();

        documentFieldRepository.deleteById(id);

        /*
         * Reorganize the remaining fields.
         */
        reorganizeOrders(documentTypeId);
    }


    private int getNextOrder(
            Long documentTypeId) {

        return documentFieldRepository
                .findByDocumentTypeId(documentTypeId)
                .stream()
                .map(DocumentField::getOrdre)
                .filter(ordre -> ordre != null)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0) + 1;
    }


    private void reorganizeOrders(
            Long documentTypeId) {

        List<DocumentField> fields =
                documentFieldRepository
                        .findByDocumentTypeId(
                                documentTypeId);

        /*
         * Sort existing fields according to
         * their current order.
         */
        fields.sort(
                Comparator.comparing(
                        DocumentField::getOrdre));

        int order = 1;

        for (DocumentField field : fields) {

            field.setOrdre(order);

            documentFieldRepository.save(field);

            order++;
        }
    }


    private void validateRequest(
            CreateDocumentFieldRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "La requête est obligatoire");
        }

        if (request.getDocumentTypeId() == null) {

            throw new IllegalArgumentException(
                    "documentTypeId est obligatoire");
        }

        if (request.getNom() == null
                || request.getNom().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le nom du champ est obligatoire");
        }

        if (request.getLibelle() == null
                || request.getLibelle().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le libellé du champ est obligatoire");
        }

        if (request.getType() == null
                || request.getType().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le type du champ est obligatoire");
        }

        String type =
                request.getType()
                        .trim()
                        .toUpperCase();

        if (!TYPES_AUTORISES.contains(type)) {

            throw new IllegalArgumentException(
                    "Type de champ invalide. "
                            + "Types autorisés : "
                            + "TEXT, TEXTAREA, DATE, NUMBER, BOOLEAN");
        }

        request.setNom(
                request.getNom().trim());

        request.setLibelle(
                request.getLibelle().trim());

        request.setType(type);
    }


    private void validateItemRequest(
            DocumentFieldItemRequest request) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "Un champ ne peut pas être null");
        }

        if (request.getNom() == null
                || request.getNom().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le nom du champ est obligatoire");
        }

        if (request.getLibelle() == null
                || request.getLibelle().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le libellé du champ est obligatoire");
        }

        if (request.getType() == null
                || request.getType().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Le type du champ est obligatoire");
        }

        String type =
                request.getType()
                        .trim()
                        .toUpperCase();

        if (!TYPES_AUTORISES.contains(type)) {

            throw new IllegalArgumentException(
                    "Type de champ invalide. "
                            + "Types autorisés : "
                            + "TEXT, TEXTAREA, DATE, NUMBER, BOOLEAN");
        }
    }


    private DocumentFieldResponse toResponse(
            DocumentField documentField) {

        DocumentFieldResponse response =
                new DocumentFieldResponse();

        response.setId(
                documentField.getId());

        if (documentField.getDocumentType() != null) {

            response.setDocumentTypeId(
                    documentField
                            .getDocumentType()
                            .getId());

            response.setDocumentTypeNom(
                    documentField
                            .getDocumentType()
                            .getNom());
        }

        response.setNom(
                documentField.getNom());

        response.setLibelle(
                documentField.getLibelle());

        response.setType(
                documentField.getType());

        response.setObligatoire(
                documentField.getObligatoire());

        response.setOrdre(
                documentField.getOrdre());

        return response;
    }
}