package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.MissionPersonne;

import java.util.List;
import java.util.Optional;

public interface MissionPersonneRepository {

    MissionPersonne save(MissionPersonne missionPersonne);

    Optional<MissionPersonne> findById(Long id);

    List<MissionPersonne> findAll();

    void deleteById(Long id);

    List<MissionPersonne> findByMissionId(Long missionId);

    void deleteByMissionId(Long missionId);
}