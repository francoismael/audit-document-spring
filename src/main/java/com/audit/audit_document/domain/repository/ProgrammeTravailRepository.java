package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.ProgrammeTravail;

import java.util.List;
import java.util.Optional;

public interface ProgrammeTravailRepository {

    ProgrammeTravail save(ProgrammeTravail programme);

    Optional<ProgrammeTravail> findById(Long id);

    Optional<ProgrammeTravail> findByMissionId(Long missionId);

    List<ProgrammeTravail> findAll();

    void deleteById(Long id);

    boolean existsByMissionId(Long missionId);
}