package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateStructureRequest;
import com.audit.audit_document.application.usecases.CreateStructureUseCase;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;

@Service
public class CreateStructureService implements CreateStructureUseCase {

    private final StructureRepository structureRepository;

    public CreateStructureService(
            StructureRepository structureRepository
    ) {
        this.structureRepository = structureRepository;
    }

    @Override
    public Structure execute(CreateStructureRequest request) {

        Structure structure = new Structure();

        String nom = request.getNom() == null ? "" : request.getNom().trim();

        if (nom.isEmpty()) {
            throw new IllegalArgumentException(
                    "Le nom de la structure est obligatoire.");
        }

        String descriptions = request.getDescriptions() == null
                ? null
                : request.getDescriptions().trim();

        structure.setNom(nom);
        structure.setDescriptions(
                descriptions == null || descriptions.isEmpty() ? null : descriptions);

        return structureRepository.save(structure);
    }
}