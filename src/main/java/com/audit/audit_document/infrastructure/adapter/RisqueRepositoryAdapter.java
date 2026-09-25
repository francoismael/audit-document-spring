package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.audit.audit_document.domain.entity.Risque;
import com.audit.audit_document.domain.repository.RisqueRepository;
import com.audit.audit_document.infrastructure.jpa.RisqueJpa;

@Component
public class RisqueRepositoryAdapter implements RisqueRepository {

    private final RisqueJpa risqueJpa;

    public RisqueRepositoryAdapter(RisqueJpa risqueJpa) {
        this.risqueJpa = risqueJpa;
    }

    @Override
    public Risque save(Risque risque) {
        return risqueJpa.save(risque);
    }

    @Override
    public Optional<Risque> findById(Long id) {
        return risqueJpa.findById(id);
    }

    @Override
    public List<Risque> findByConstatId(Long constatId) {
        return risqueJpa.findByConstatId(constatId);
    }

    @Override
    public void deleteById(Long id) {
        risqueJpa.deleteById(id);
    }

    @Override
    public void deleteByConstatId(Long constatId) {
        risqueJpa.deleteByConstatId(constatId);
    }
}