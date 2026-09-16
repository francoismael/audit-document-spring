package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.DeclarationIndependance;

import java.util.List;
import java.util.Optional;

public interface DeclarationIndependanceRepository {
    DeclarationIndependance save(DeclarationIndependance declaration);
    Optional<DeclarationIndependance> findById(Long id);
    List<DeclarationIndependance> findAll();
    DeclarationIndependance update(DeclarationIndependance declaration);
    void deleteById(Long id);
}