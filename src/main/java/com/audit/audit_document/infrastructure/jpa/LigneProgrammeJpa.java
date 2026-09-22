package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.LigneProgramme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LigneProgrammeJpa
        extends JpaRepository<LigneProgramme, Long> {

    List<LigneProgramme> findByProgrammeId(Long programmeId);

    void deleteByProgrammeId(Long programmeId);

    @Query(
        value = "SELECT nextval('ligne_programme_controle_seq')",
        nativeQuery = true
    )
    Long getNextNumeroControle();
}