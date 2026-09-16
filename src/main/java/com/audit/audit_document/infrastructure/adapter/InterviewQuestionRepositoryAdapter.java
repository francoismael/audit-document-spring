package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.InterviewQuestion;
import com.audit.audit_document.domain.repository.InterviewQuestionRepository;
import com.audit.audit_document.infrastructure.jpa.InterviewQuestionJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InterviewQuestionRepositoryAdapter implements InterviewQuestionRepository {

    private final InterviewQuestionJpa repository;

    public InterviewQuestionRepositoryAdapter(InterviewQuestionJpa repository) {
        this.repository = repository;
    }

    @Override
    public InterviewQuestion save(InterviewQuestion question) {
        return repository.save(question);
    }

    @Override
    public Optional<InterviewQuestion> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<InterviewQuestion> findByInterviewId(Long interviewId) {
        return repository.findByInterviewId(interviewId);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByInterviewId(Long interviewId) {
        repository.deleteByInterviewId(interviewId);
    }

    @Override
    public void flush() {
    repository.flush();
    }
}