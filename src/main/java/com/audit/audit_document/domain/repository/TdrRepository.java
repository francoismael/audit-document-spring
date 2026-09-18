package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Tdr;

import java.util.List;
import java.util.Optional;

public interface TdrRepository {

    Tdr save(Tdr tdr);

    Optional<Tdr> findById(Long id);

    List<Tdr> findAll();

    void deleteById(Long id);

    boolean existsByMissionId(Long missionId);
}