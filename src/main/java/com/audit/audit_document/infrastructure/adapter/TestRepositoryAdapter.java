package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Test;
import com.audit.audit_document.domain.repository.TestRepository;
import com.audit.audit_document.infrastructure.jpa.TestJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestRepositoryAdapter
        implements TestRepository {

    private final TestJpa testJpa;

    public TestRepositoryAdapter(TestJpa testJpa) {
        this.testJpa = testJpa;
    }

    @Override
    public Test save(Test test) {
        return testJpa.save(test);
    }

    @Override
    public Optional<Test> findById(Long id) {
        return testJpa.findById(id);
    }

    @Override
    public List<Test> findAll() {
        return testJpa.findAll();
    }

    @Override
    public List<Test> findByLigneProgrammeId(
            Long ligneProgrammeId) {

        return testJpa.findByLigneProgrammeId(
                ligneProgrammeId
        );
    }

    @Override
    public void deleteById(Long id) {
        testJpa.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return testJpa.existsById(id);
    }

    @Override
    public String getNextReference() {

        Long number = testJpa.getNextReference();

        return String.format(
                "FT%03d",
                number
        );
    }
}