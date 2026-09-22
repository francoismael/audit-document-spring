package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.LigneProgramme;

import java.util.List;
import java.util.Optional;

public interface LigneProgrammeRepository {

    LigneProgramme save(LigneProgramme ligne);

    Optional<LigneProgramme> findById(Long id);

    List<LigneProgramme> findByProgrammeId(Long programmeId);

    void deleteById(Long id);

    void deleteByProgrammeId(Long programmeId);

    String getNextNumeroControle();
}