package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentTypeJpa
        extends JpaRepository<DocumentType, Long> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}