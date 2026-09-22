package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.ProgrammeTravail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgrammeTravailJpa
        extends JpaRepository<ProgrammeTravail, Long> {

    Optional<ProgrammeTravail> findByMissionId(Long missionId);

    boolean existsByMissionId(Long missionId);
}