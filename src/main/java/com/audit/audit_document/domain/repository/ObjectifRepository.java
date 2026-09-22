package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Objectif;

import java.util.List;
import java.util.Optional;

public interface ObjectifRepository {

    Objectif save(Objectif objectif);

    Optional<Objectif> findById(Long id);

    List<Objectif> findByTdrId(Long tdrId);

    void deleteByTdrId(Long tdrId);

    void flush();

    Integer getNextNumero(Long tdrId);
}