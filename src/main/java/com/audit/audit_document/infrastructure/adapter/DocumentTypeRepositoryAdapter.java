package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.DocumentType;
import com.audit.audit_document.domain.repository.DocumentTypeRepository;
import com.audit.audit_document.infrastructure.jpa.DocumentTypeJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DocumentTypeRepositoryAdapter
        implements DocumentTypeRepository {

    private final DocumentTypeJpa jpa;

    public DocumentTypeRepositoryAdapter(
            DocumentTypeJpa jpa) {

        this.jpa = jpa;
    }

    @Override
    public DocumentType save(
            DocumentType documentType) {

        return jpa.save(documentType);
    }

    @Override
    public Optional<DocumentType> findById(
            Long id) {

        return jpa.findById(id);
    }

    @Override
    public List<DocumentType> findAll() {

        return jpa.findAll();
    }

    @Override
    public boolean existsByCode(
            String code) {

        return jpa.existsByCode(code);
    }

    @Override
    public boolean existsByCodeAndIdNot(
            String code,
            Long id) {

        return jpa.existsByCodeAndIdNot(code, id);
    }

    @Override
    public void deleteById(Long id) {

        jpa.deleteById(id);
    }
}