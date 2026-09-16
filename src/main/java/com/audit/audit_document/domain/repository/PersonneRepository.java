package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Personne;

import java.util.List;
import java.util.Optional;

public interface PersonneRepository {

    Personne save(Personne personne);

    Optional<Personne> findById(Long id);

    List<Personne> findAll();

    void deleteById(Long id);
}