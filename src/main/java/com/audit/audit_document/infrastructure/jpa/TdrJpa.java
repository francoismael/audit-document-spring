package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Tdr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TdrJpa
        extends JpaRepository<Tdr, Long> {

    boolean existsByMissionId(Long missionId);
}