package com.audit.audit_document.infrastructure.repository;

import com.audit.audit_document.domain.entity.Structure;
import com.audit.audit_document.domain.repository.StructureRepository;
import org.springframework.stereotype.Repository;
import com.audit.audit_document.infrastructure.jpa.StructureJpa;
import java.util.List;
import java.util.Optional;

@Repository
public class StructureRepositoryAdapter implements StructureRepository {

    private final StructureJpa repository;

    public StructureRepositoryAdapter(StructureJpa repository) {
        this.repository = repository;
    }

    @Override
    public Structure save(Structure structure) {
        return repository.save(structure);
    }

    @Override
    public Optional<Structure> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Structure> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}