package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReunionJpa extends JpaRepository<Reunion, Long> {
}