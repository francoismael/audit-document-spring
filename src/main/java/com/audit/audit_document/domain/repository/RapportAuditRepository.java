package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.RapportAudit;

import java.util.List;
import java.util.Optional;

public interface RapportAuditRepository {

    RapportAudit save(RapportAudit rapportAudit);

    Optional<RapportAudit> findById(Long id);

    Optional<RapportAudit> findByMissionId(Long missionId);

    List<RapportAudit> findAll();

    void deleteById(Long id);
}