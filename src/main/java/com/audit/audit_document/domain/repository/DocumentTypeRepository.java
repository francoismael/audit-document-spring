package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.DocumentType;

import java.util.List;
import java.util.Optional;

public interface DocumentTypeRepository {

    DocumentType save(DocumentType documentType);

    Optional<DocumentType> findById(Long id);

    List<DocumentType> findAll();

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);

    void deleteById(Long id);
}