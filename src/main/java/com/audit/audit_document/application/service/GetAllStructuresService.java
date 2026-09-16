package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.GetAllStructuresUseCase;
import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.StructureRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllStructuresService implements GetAllStructuresUseCase {

    private final StructureRepository structureRepository;

    public GetAllStructuresService(
            StructureRepository structureRepository
    ) {
        this.structureRepository = structureRepository;
    }

    @Override
    public List<Structure> execute() {

        return structureRepository.findAll();
    }
}