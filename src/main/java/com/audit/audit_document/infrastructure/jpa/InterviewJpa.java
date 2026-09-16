package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewJpa extends JpaRepository<Interview, Long> {

    @Query(
        value = "SELECT nextval('interview_reference_seq')",
        nativeQuery = true
    )
    Long getNextReferenceNumber();
}