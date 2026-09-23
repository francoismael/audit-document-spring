package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestJpa extends JpaRepository<Test, Long> {

    List<Test> findByLigneProgrammeId(Long ligneProgrammeId);

    @Query(
        value = "SELECT nextval('test_reference_seq')",
        nativeQuery = true
    )
    Long getNextReference();
}