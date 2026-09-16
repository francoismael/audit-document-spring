package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionJpa extends JpaRepository<Mission, Long> {

    @Query(
        value = "SELECT nextval('mission_numero_seq')",
        nativeQuery = true
    )
    Long getNextNumero();
}