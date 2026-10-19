package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ParametresResponse;
import com.audit.audit_document.application.usecases.GetParametresUseCase;
import com.audit.audit_document.domain.entity.MissionRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ParametresService implements GetParametresUseCase {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.storage.templates-dir:storage/templates}")
    private String templatesDirectory;

    @Value("${app.storage.generated-dir:storage/generated}")
    private String generatedDirectory;

    @Value("${spring.servlet.multipart.max-file-size:1MB}")
    private String maxFileSize;

    @Override
    public ParametresResponse getSettings() {

        ParametresResponse response = new ParametresResponse();

        response.setNomApplication(applicationName);
        response.setDossierModeles(templatesDirectory);
        response.setDossierDocumentsGeneres(generatedDirectory);
        response.setTailleMaxFichier(maxFileSize);
        response.setFormatsModeles(Collections.singletonList(".docx"));
        response.setFormatsGeneration(Collections.singletonList("DOCX"));
        response.setSyntaxeVariables("{{nomDuChamp}}");
        response.setTypesChamps(sortedFieldTypes());
        response.setRolesMission(missionRoles());

        return response;
    }

    private List<String> sortedFieldTypes() {

        List<String> types = new ArrayList<>(DocumentFieldService.TYPES_AUTORISES);
        Collections.sort(types);

        return types;
    }

    private List<String> missionRoles() {

        List<String> roles = new ArrayList<>();

        for (MissionRole role : MissionRole.values()) {
            roles.add(role.name());
        }

        return roles;
    }
}
