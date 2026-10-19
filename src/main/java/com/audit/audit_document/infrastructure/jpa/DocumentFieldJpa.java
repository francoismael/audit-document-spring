package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.DocumentField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentFieldJpa
        extends JpaRepository<DocumentField, Long> {

    List<DocumentField> findByDocumentTypeIdOrderByOrdreAsc(
            Long documentTypeId);
}