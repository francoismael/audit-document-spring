package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Test;

import java.util.List;
import java.util.Optional;

public interface TestRepository {

    Test save(Test test);

    Optional<Test> findById(Long id);

    List<Test> findAll();

    List<Test> findByLigneProgrammeId(Long ligneProgrammeId);

    void deleteById(Long id);

    boolean existsById(Long id);

    String getNextReference();
}