package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.OrdreSectionDocument;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdreSectionDocumentJpa extends JpaRepository<OrdreSectionDocument, Long> {

    List<OrdreSectionDocument> findAllByOrderByDocumentCodeAscPositionAsc();

    List<OrdreSectionDocument> findByDocumentCodeOrderByPositionAsc(String documentCode);

    void deleteByDocumentCode(String documentCode);
}
