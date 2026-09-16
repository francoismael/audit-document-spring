package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteStructureUseCase;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;

@Service
public class DeleteStructureService implements DeleteStructureUseCase {

    private final StructureRepository structureRepository;

    public DeleteStructureService(
            StructureRepository structureRepository
    ) {
        this.structureRepository = structureRepository;
    }

    @Override
    public void execute(Long id) {

        Structure structure = structureRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Structure introuvable avec l'id : " + id
                        )
                );

        structureRepository.deleteById(structure.getId());
    }
}