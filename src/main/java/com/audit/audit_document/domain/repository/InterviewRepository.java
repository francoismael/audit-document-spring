package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Interview;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository {

    Interview save(Interview interview);

    Optional<Interview> findById(Long id);

    List<Interview> findAll();

    void deleteById(Long id);

    long getNextReferenceNumber();
}