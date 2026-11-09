package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.MissionPersonne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionPersonneJpa
        extends JpaRepository<MissionPersonne, Long> {

    List<MissionPersonne> findByMissionId(Long missionId);
    void deleteByMissionId(Long missionId);

    @Query("select distinct mp.mission.id from MissionPersonne mp where mp.personne.id = :personneId")
    List<Long> findMissionIdsByPersonneId(@Param("personneId") Long personneId);
}