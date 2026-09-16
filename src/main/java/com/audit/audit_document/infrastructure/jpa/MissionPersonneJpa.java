package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.MissionPersonne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionPersonneJpa
        extends JpaRepository<MissionPersonne, Long> {

    List<MissionPersonne> findByMissionId(Long missionId);
    void deleteByMissionId(Long missionId);
}