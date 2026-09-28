package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.RapportAudit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RapportAuditJpa
        extends JpaRepository<RapportAudit, Long> {

    Optional<RapportAudit> findByMissionId(Long missionId);
}