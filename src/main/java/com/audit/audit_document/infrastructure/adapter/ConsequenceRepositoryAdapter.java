package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.audit.audit_document.domain.entity.Consequence;
import com.audit.audit_document.domain.repository.ConsequenceRepository;
import com.audit.audit_document.infrastructure.jpa.ConsequenceJpa;

@Component
public class ConsequenceRepositoryAdapter implements ConsequenceRepository {

    private final ConsequenceJpa consequenceJpa;

    public ConsequenceRepositoryAdapter(ConsequenceJpa consequenceJpa) {
        this.consequenceJpa = consequenceJpa;
    }

    @Override
    public Consequence save(Consequence consequence) {
        return consequenceJpa.save(consequence);
    }

    @Override
    public Optional<Consequence> findById(Long id) {
        return consequenceJpa.findById(id);
    }

    @Override
    public List<Consequence> findByConstatId(Long constatId) {
        return consequenceJpa.findByConstatId(constatId);
    }

    @Override
    public void deleteById(Long id) {
        consequenceJpa.deleteById(id);
    }

    @Override
    public void deleteByConstatId(Long constatId) {
        consequenceJpa.deleteByConstatId(constatId);
    }
}