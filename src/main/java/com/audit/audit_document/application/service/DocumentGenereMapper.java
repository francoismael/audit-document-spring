package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.DocumentGenereResponse;
import com.audit.audit_document.domain.entity.DocumentGenere;
import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.entity.Mission;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class DocumentGenereMapper {

    private static final TypeReference<Map<String, Object>> VALUES_TYPE =
            new TypeReference<Map<String, Object>>() {
            };

    private final ObjectMapper objectMapper;

    public DocumentGenereMapper(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public DocumentGenereResponse toResponse(DocumentGenere document) {

        DocumentGenereResponse response = new DocumentGenereResponse();

        response.setId(document.getId());
        DocumentType type = document.getDocumentType();

        // Without its type (deleted), the document gives the code and the name kept with it.
        if (type != null) {

            response.setDocumentTypeId(type.getId());
            response.setDocumentTypeCode(type.getCode());
            response.setDocumentTypeNom(type.getNom());

        } else {

            response.setDocumentTypeCode(document.getDocumentTypeCode());
            response.setDocumentTypeNom(document.getDocumentTypeNom());
        }
        response.setNomFichier(document.getNomFichier());
        response.setTaille(document.getTaille());
        response.setTemplateVersion(document.getTemplateVersion());
        response.setDateGeneration(document.getDateGeneration());
        response.setValeurs(readValues(document.getValeurs()));

        Mission mission = document.getMission();

        if (mission != null) {

            response.setMissionId(mission.getId());
            response.setMissionNumero(mission.getNumero());
            response.setMissionIntitule(mission.getIntitule());
        }

        return response;
    }

    private Map<String, Object> readValues(String json) {

        if (json == null || json.isEmpty()) {
            return Collections.emptyMap();
        }

        try {
            return objectMapper.readValue(json, VALUES_TYPE);

        } catch (JsonProcessingException e) {
            return Collections.emptyMap();
        }
    }
}
