package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.GetStructureByIdUseCase;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;

@Service
public class GetStructureByIdService implements GetStructureByIdUseCase {

    private final StructureRepository structureRepository;

    public GetStructureByIdService(
            StructureRepository structureRepository
    ) {
        this.structureRepository = structureRepository;
    }

    @Override
    public Structure execute(Long id) {

        return structureRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Structure introuvable avec l'id : " + id
                        )
                );
    }
}