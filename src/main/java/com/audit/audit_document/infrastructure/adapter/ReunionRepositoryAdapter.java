package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Reunion;
import com.audit.audit_document.domain.repository.ReunionRepository;
import com.audit.audit_document.infrastructure.jpa.ReunionJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReunionRepositoryAdapter implements ReunionRepository {

    private final ReunionJpa repository;

    public ReunionRepositoryAdapter(ReunionJpa repository) {
        this.repository = repository;
    }

    @Override
    public Reunion save(Reunion reunion) {
        return repository.save(reunion);
    }

    @Override
    public Optional<Reunion> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Reunion> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}