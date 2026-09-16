package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UpdateStructureRequest;
import com.audit.audit_document.application.usecases.UpdateStructureUseCase;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;

@Service
public class UpdateStructureService implements UpdateStructureUseCase {

    private final StructureRepository structureRepository;

    public UpdateStructureService(
            StructureRepository structureRepository
    ) {
        this.structureRepository = structureRepository;
    }

    @Override
    public Structure execute(
            Long id,
            UpdateStructureRequest request
    ) {

        Structure structure = structureRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Structure introuvable avec l'id : " + id
                        )
                );

        structure.setNom(request.getNom());
        structure.setDescriptions(request.getDescriptions());

        return structureRepository.save(structure);
    }
}