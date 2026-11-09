package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.OrdreSectionDocument;

import java.util.List;

public interface OrdreSectionDocumentRepository {

    // Every saved section, by document then by position.
    List<OrdreSectionDocument> findAll();

    List<OrdreSectionDocument> findByDocumentCode(String documentCode);

    List<OrdreSectionDocument> saveAll(List<OrdreSectionDocument> sections);

    void deleteByDocumentCode(String documentCode);

    void flush();
}
