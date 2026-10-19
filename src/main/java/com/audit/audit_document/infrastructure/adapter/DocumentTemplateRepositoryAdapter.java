package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.DocumentTemplate;
import com.audit.audit_document.domain.repository.DocumentTemplateRepository;
import com.audit.audit_document.infrastructure.jpa.DocumentTemplateJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentTemplateRepositoryAdapter
        implements DocumentTemplateRepository {

    private final DocumentTemplateJpa jpa;

    public DocumentTemplateRepositoryAdapter(
            DocumentTemplateJpa jpa) {

        this.jpa = jpa;
    }

    @Override
    public DocumentTemplate save(
            DocumentTemplate template) {

        return jpa.save(template);
    }

    @Override
    public Optional<DocumentTemplate> findById(
            Long id) {

        return jpa.findById(id);
    }

    @Override
    public List<DocumentTemplate>
    findByDocumentTypeId(Long documentTypeId) {

        return jpa.findByDocumentTypeId(
                documentTypeId);
    }

    @Override
    public Optional<DocumentTemplate>
    findActiveByDocumentTypeId(
            Long documentTypeId) {

        return jpa
                .findByDocumentTypeIdAndActifTrue(
                        documentTypeId);
    }

    @Override
    public void deactivateByDocumentTypeId(
            Long documentTypeId) {

        jpa.deactivateByDocumentTypeId(
                documentTypeId);
    }
}