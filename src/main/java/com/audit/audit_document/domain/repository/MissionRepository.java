package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Mission;

import java.util.List;
import java.util.Optional;

public interface MissionRepository {

    Mission save(Mission mission);

    Optional<Mission> findById(Long id);

    List<Mission> findAll();

    void deleteById(Long id);

    long getNextNumero();
}