package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Structure;

import java.util.List;
import java.util.Optional;

public interface StructureRepository {

    Structure save(Structure structure);

    Optional<Structure> findById(Long id);

    List<Structure> findAll();

    void deleteById(Long id);
}