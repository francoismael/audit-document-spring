package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Objectif;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ObjectifJpa extends JpaRepository<Objectif, Long> {

    List<Objectif> findByTdrId(Long tdrId);

    void deleteByTdrId(Long tdrId);

    @Query(
        value = "SELECT COALESCE(MAX(CAST(SUBSTRING(numero, 5) AS INTEGER)), 0) + 1 " +
                "FROM objectif WHERE tdr_id = :tdrId",
        nativeQuery = true
    )
    Integer getNextNumero(@Param("tdrId") Long tdrId);
}