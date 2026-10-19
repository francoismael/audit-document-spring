package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.DocumentField;
import com.audit.audit_document.domain.repository.DocumentFieldRepository;
import com.audit.audit_document.infrastructure.jpa.DocumentFieldJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentFieldRepositoryAdapter
        implements DocumentFieldRepository {

    private final DocumentFieldJpa documentFieldJpa;

    public DocumentFieldRepositoryAdapter(
            DocumentFieldJpa documentFieldJpa) {

        this.documentFieldJpa = documentFieldJpa;
    }

    @Override
    public DocumentField save(
            DocumentField documentField) {

        return documentFieldJpa.save(documentField);
    }

    @Override
    public Optional<DocumentField> findById(
            Long id) {

        return documentFieldJpa.findById(id);
    }

    @Override
    public List<DocumentField> findByDocumentTypeId(
            Long documentTypeId) {

        return documentFieldJpa
                .findByDocumentTypeIdOrderByOrdreAsc(
                        documentTypeId);
    }

    @Override
    public void deleteById(Long id) {

        documentFieldJpa.deleteById(id);
    }
}