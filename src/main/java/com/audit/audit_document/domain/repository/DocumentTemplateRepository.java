package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.DocumentTemplate;

import java.util.List;
import java.util.Optional;

public interface DocumentTemplateRepository {

    DocumentTemplate save(
            DocumentTemplate template);

    Optional<DocumentTemplate> findById(Long id);

    List<DocumentTemplate> findByDocumentTypeId(
            Long documentTypeId);

    Optional<DocumentTemplate>
    findActiveByDocumentTypeId(
            Long documentTypeId);

    void deactivateByDocumentTypeId(
            Long documentTypeId);
}