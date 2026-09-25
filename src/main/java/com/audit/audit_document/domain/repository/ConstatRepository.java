package com.audit.audit_document.domain.repository;

import java.util.List;
import java.util.Optional;

import com.audit.audit_document.domain.entity.Constat;

public interface ConstatRepository {

    Constat save(Constat constat);

    Optional<Constat> findById(Long id);

    List<Constat> findAll();

    List<Constat> findByTestId(Long testId);

    void deleteById(Long id);

    boolean existsById(Long id);

    Long getNextReference();
}