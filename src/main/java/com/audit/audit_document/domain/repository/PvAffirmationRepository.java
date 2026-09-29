package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.PvAffirmation;

import java.util.Optional;

public interface PvAffirmationRepository {

    PvAffirmation save(PvAffirmation pvAffirmation);

    Optional<PvAffirmation> findById(Long id);

    Optional<PvAffirmation> findByMissionId(Long missionId);

    void deleteById(Long id);
}