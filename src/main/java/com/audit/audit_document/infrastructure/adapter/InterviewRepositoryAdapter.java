package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Interview;
import com.audit.audit_document.domain.repository.InterviewRepository;
import com.audit.audit_document.infrastructure.jpa.InterviewJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InterviewRepositoryAdapter implements InterviewRepository {

    private final InterviewJpa repository;

    public InterviewRepositoryAdapter(InterviewJpa repository) {
        this.repository = repository;
    }

    @Override
    public Interview save(Interview interview) {
        return repository.save(interview);
    }

    @Override
    public Optional<Interview> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Interview> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long getNextReferenceNumber() {
    return repository.getNextReferenceNumber();
    }
}