package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.DocumentTypeResponse;
import com.audit.audit_document.application.usecases.GetAllDocumentTypesUseCase;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GetAllDocumentTypesService
        implements GetAllDocumentTypesUseCase {

    private final DocumentTypeRepository repository;

    private final DocumentGenereRepository documentGenereRepository;

    public GetAllDocumentTypesService(
            DocumentTypeRepository repository,
            DocumentGenereRepository documentGenereRepository) {

        this.repository = repository;
        this.documentGenereRepository = documentGenereRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeResponse> execute(String query, Boolean actif, Boolean genere) {

        // Generated documents by type, read only when the filter needs them.
        Map<Long, Long> generatedCounts =
                genere != null
                        ? documentGenereRepository.countGroupedByDocumentType()
                        : Collections.<Long, Long>emptyMap();

        List<DocumentTypeResponse> responses =
                new ArrayList<>();

        for (DocumentType type : repository.findAll()) {

            boolean used = generatedCounts.getOrDefault(type.getId(), 0L) > 0;

            if ((actif != null && !actif.equals(type.getActif()))
                    || (genere != null && genere != used)
                    || !SearchText.matches(query, type.getNom(), type.getCode(), type.getDescription())) {
                continue;
            }

            DocumentTypeResponse response =
                    new DocumentTypeResponse();

            response.setId(type.getId());
            response.setCode(type.getCode());
            response.setNom(type.getNom());
            response.setDescription(
                    type.getDescription());
            response.setActif(type.getActif());
            response.setDateCreation(
                    type.getDateCreation());

            responses.add(response);
        }

        return responses;
    }
}
