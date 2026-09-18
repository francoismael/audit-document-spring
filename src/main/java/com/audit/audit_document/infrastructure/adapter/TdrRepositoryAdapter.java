package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Tdr;
import com.audit.audit_document.domain.repository.TdrRepository;
import com.audit.audit_document.infrastructure.jpa.TdrJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TdrRepositoryAdapter
        implements TdrRepository {

    private final TdrJpa repository;

    public TdrRepositoryAdapter(TdrJpa repository) {
        this.repository = repository;
    }

    @Override
    public Tdr save(Tdr tdr) {
        return repository.save(tdr);
    }

    @Override
    public Optional<Tdr> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Tdr> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByMissionId(Long missionId) {
        return repository.existsByMissionId(missionId);
    }
}