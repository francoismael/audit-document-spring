package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.OrdreSectionDocument;
import com.audit.audit_document.domain.repository.OrdreSectionDocumentRepository;
import com.audit.audit_document.infrastructure.jpa.OrdreSectionDocumentJpa;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdreSectionDocumentRepositoryAdapter
        implements OrdreSectionDocumentRepository {

    private final OrdreSectionDocumentJpa repository;

    public OrdreSectionDocumentRepositoryAdapter(OrdreSectionDocumentJpa repository) {
        this.repository = repository;
    }

    @Override
    public List<OrdreSectionDocument> findAll() {
        return repository.findAllByOrderByDocumentCodeAscPositionAsc();
    }

    @Override
    public List<OrdreSectionDocument> findByDocumentCode(String documentCode) {
        return repository.findByDocumentCodeOrderByPositionAsc(documentCode);
    }

    @Override
    public List<OrdreSectionDocument> saveAll(List<OrdreSectionDocument> sections) {
        return repository.saveAll(sections);
    }

    @Override
    public void deleteByDocumentCode(String documentCode) {
        repository.deleteByDocumentCode(documentCode);
    }

    @Override
    public void flush() {
        repository.flush();
    }
}
