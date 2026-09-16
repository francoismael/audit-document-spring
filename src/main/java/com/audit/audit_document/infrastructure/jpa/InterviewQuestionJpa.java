package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewQuestionJpa extends JpaRepository<InterviewQuestion, Long> {

    List<InterviewQuestion> findByInterviewId(Long interviewId);

    void deleteByInterviewId(Long interviewId);
}