package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.dto.DocumentFieldResponse;
import com.audit.audit_document.application.dto.DocumentTemplateResponse;
import com.audit.audit_document.application.dto.DocumentTypeFormResponse;
import com.audit.audit_document.application.dto.DocumentTypeResponse;
import com.audit.audit_document.application.usecases.GetDocumentTypeFormUseCase;
import com.audit.audit_document.domain.entity.DocumentField;
import com.audit.audit_document.domain.entity.DocumentTemplate;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentFieldRepository;
import com.audit.audit_document.domain.repository.DocumentTemplateRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GetDocumentTypeFormService
        implements GetDocumentTypeFormUseCase {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTemplateRepository documentTemplateRepository;
    private final DocumentFieldRepository documentFieldRepository;

    public GetDocumentTypeFormService(
            DocumentTypeRepository documentTypeRepository,
            DocumentTemplateRepository documentTemplateRepository,
            DocumentFieldRepository documentFieldRepository) {

        this.documentTypeRepository =
                documentTypeRepository;

        this.documentTemplateRepository =
                documentTemplateRepository;

        this.documentFieldRepository =
                documentFieldRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentTypeFormResponse getForm(
            Long documentTypeId) {

        if (documentTypeId == null) {

            throw new IllegalArgumentException(
                    "L'identifiant du type de document est obligatoire");
        }

        /*
         * 1. Retrieve the document type.
         */
        DocumentType documentType =
                documentTypeRepository
                        .findById(documentTypeId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Type de document introuvable"));

        /*
         * 2. Retrieve the active template, if any.
         * The form is also used to configure a type, so a type
         * without template or a disabled type must still load.
         * Generation is the step that rejects those cases.
         */
        DocumentTemplate template =
                documentTemplateRepository
                        .findActiveByDocumentTypeId(
                                documentTypeId)
                        .orElse(null);

        /*
         * 3. Retrieve all fields.
         */
        List<DocumentField> documentFields =
                documentFieldRepository
                        .findByDocumentTypeId(
                                documentTypeId);

        /*
         * 4. Sort fields according to their order.
         */
        documentFields.sort(
                Comparator.comparing(
                        DocumentField::getOrdre,
                        Comparator.nullsLast(Comparator.naturalOrder())));

        /*
         * 5. Convert fields to responses.
         */
        List<DocumentFieldResponse> fields =
                new ArrayList<>();

        for (DocumentField field : documentFields) {

            DocumentFieldResponse response =
                    new DocumentFieldResponse();

            response.setId(field.getId());

            response.setDocumentTypeId(
                    documentType.getId());

            response.setDocumentTypeNom(
                    documentType.getNom());

            response.setNom(
                    field.getNom());

            response.setLibelle(
                    field.getLibelle());

            response.setType(
                    field.getType());

            response.setObligatoire(
                    field.getObligatoire());

            response.setOrdre(
                    field.getOrdre());

            fields.add(response);
        }

        /*
         * 6. Build the final response.
         */
        DocumentTypeFormResponse response =
                new DocumentTypeFormResponse();

        response.setDocumentType(
                toDocumentTypeResponse(documentType));

        response.setTemplate(
                template != null
                        ? toDocumentTemplateResponse(template)
                        : null);

        response.setFields(fields);

        return response;
    }

    private DocumentTypeResponse toDocumentTypeResponse(
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

    private DocumentTemplateResponse toDocumentTemplateResponse(
            DocumentTemplate template) {

        DocumentTemplateResponse response =
                new DocumentTemplateResponse();

        response.setId(
                template.getId());

        if (template.getDocumentType() != null) {

            response.setDocumentTypeId(
                    template.getDocumentType().getId());

            response.setDocumentTypeNom(
                    template.getDocumentType().getNom());
        }

        response.setNomFichierOriginal(
                template.getNomFichierOriginal());

        response.setNomFichierStockage(
                template.getNomFichierStockage());

        response.setCheminFichier(
                template.getCheminFichier());

        response.setTypeMime(
                template.getTypeMime());

        response.setTaille(
                template.getTaille());

        response.setVersion(
                template.getVersion());

        response.setActif(
                template.getActif());

        response.setDateUpload(
                template.getDateUpload());

        return response;
    }
}