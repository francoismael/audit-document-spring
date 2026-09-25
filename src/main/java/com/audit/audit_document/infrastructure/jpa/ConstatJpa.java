package com.audit.audit_document.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.audit.audit_document.domain.entity.Constat;

public interface ConstatJpa extends JpaRepository<Constat, Long> {

    List<Constat> findByTestId(Long testId);

    @Query(
        value = "SELECT nextval('constat_reference_seq')",
        nativeQuery = true
    )
    Long getNextReference();
}