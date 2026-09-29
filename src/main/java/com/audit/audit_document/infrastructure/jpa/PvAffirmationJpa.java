package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.PvAffirmation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PvAffirmationJpa
        extends JpaRepository<PvAffirmation, Long> {

    Optional<PvAffirmation> findByMissionId(Long missionId);
}