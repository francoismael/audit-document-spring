package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.audit.audit_document.domain.entity.Cause;
import com.audit.audit_document.domain.repository.CauseRepository;
import com.audit.audit_document.infrastructure.jpa.CauseJpa;

@Component
public class CauseRepositoryAdapter implements CauseRepository {

    private final CauseJpa causeJpa;

    public CauseRepositoryAdapter(CauseJpa causeJpa) {
        this.causeJpa = causeJpa;
    }

    @Override
    public Cause save(Cause cause) {
        return causeJpa.save(cause);
    }

    @Override
    public Optional<Cause> findById(Long id) {
        return causeJpa.findById(id);
    }

    @Override
    public List<Cause> findByConstatId(Long constatId) {
        return causeJpa.findByConstatId(constatId);
    }

    @Override
    public void deleteById(Long id) {
        causeJpa.deleteById(id);
    }

    @Override
    public void deleteByConstatId(Long constatId) {
        causeJpa.deleteByConstatId(constatId);
    }
}