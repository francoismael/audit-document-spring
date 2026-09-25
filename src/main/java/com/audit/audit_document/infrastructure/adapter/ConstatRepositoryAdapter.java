package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.audit.audit_document.domain.entity.Constat;
import com.audit.audit_document.domain.repository.ConstatRepository;
import com.audit.audit_document.infrastructure.jpa.ConstatJpa;

@Component
public class ConstatRepositoryAdapter implements ConstatRepository {

    private final ConstatJpa constatJpa;

    public ConstatRepositoryAdapter(ConstatJpa constatJpa) {
        this.constatJpa = constatJpa;
    }

    @Override
    public Constat save(Constat constat) {
        return constatJpa.save(constat);
    }

    @Override
    public Optional<Constat> findById(Long id) {
        return constatJpa.findById(id);
    }

    @Override
    public List<Constat> findAll() {
        return constatJpa.findAll();
    }

    @Override
    public List<Constat> findByTestId(Long testId) {
        return constatJpa.findByTestId(testId);
    }

    @Override
    public void deleteById(Long id) {
        constatJpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return constatJpa.existsById(id);
    }

    @Override
    public Long getNextReference() {
        return constatJpa.getNextReference();
    }
}