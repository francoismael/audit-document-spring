package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Echantillon;

import java.util.List;
import java.util.Optional;

public interface EchantillonRepository {

    Echantillon save(Echantillon echantillon);

    Optional<Echantillon> findById(Long id);

    List<Echantillon> findByTestId(Long testId);

    void deleteById(Long id);

    void deleteByTestId(Long testId);

    Integer getNextNumero(Long testId);

    String getNextReference();
}