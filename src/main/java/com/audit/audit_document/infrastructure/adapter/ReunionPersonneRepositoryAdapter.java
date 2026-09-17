package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.ReunionPersonne;
import com.audit.audit_document.domain.repository.ReunionPersonneRepository;
import com.audit.audit_document.infrastructure.jpa.ReunionPersonneJpa;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReunionPersonneRepositoryAdapter
        implements ReunionPersonneRepository {

    private final ReunionPersonneJpa repository;

    public ReunionPersonneRepositoryAdapter(
            ReunionPersonneJpa repository) {
        this.repository = repository;
    }

    @Override
    public ReunionPersonne save(ReunionPersonne reunionPersonne) {
        return repository.save(reunionPersonne);
    }

    @Override
    public List<ReunionPersonne> findByReunionId(Long reunionId) {
        return repository.findByReunionId(reunionId);
    }

    @Override
    public void deleteByReunionId(Long reunionId) {
        repository.deleteByReunionId(reunionId);
    }

    @Override
    public void flush() {
        repository.flush();
    }
}