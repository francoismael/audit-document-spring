package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Objectif;
import com.audit.audit_document.domain.repository.ObjectifRepository;
import com.audit.audit_document.infrastructure.jpa.ObjectifJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ObjectifRepositoryAdapter
        implements ObjectifRepository {

    private final ObjectifJpa repository;

    public ObjectifRepositoryAdapter(ObjectifJpa repository) {
        this.repository = repository;
    }

    @Override
    public Objectif save(Objectif objectif) {
        return repository.save(objectif);
    }

    @Override
    public Optional<Objectif> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Objectif> findByTdrId(Long tdrId) {
        return repository.findByTdrId(tdrId);
    }

    @Override
    public void deleteByTdrId(Long tdrId) {
        repository.deleteByTdrId(tdrId);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public Integer getNextNumero(Long tdrId) {
        return repository.getNextNumero(tdrId);
    }
}