package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StructureJpa
        extends JpaRepository<Structure, Long> {
}