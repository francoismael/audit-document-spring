package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.InterviewQuestion;

import java.util.List;
import java.util.Optional;

public interface InterviewQuestionRepository {

    InterviewQuestion save(InterviewQuestion question);

    Optional<InterviewQuestion> findById(Long id);

    List<InterviewQuestion> findByInterviewId(Long interviewId);

    void deleteById(Long id);

    void deleteByInterviewId(Long interviewId);

    void flush();
}