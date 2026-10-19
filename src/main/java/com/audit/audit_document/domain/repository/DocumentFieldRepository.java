package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.DocumentField;

import java.util.List;
import java.util.Optional;

public interface DocumentFieldRepository {

    DocumentField save(DocumentField documentField);

    Optional<DocumentField> findById(Long id);

    List<DocumentField> findByDocumentTypeId(
            Long documentTypeId);

    void deleteById(Long id);
}