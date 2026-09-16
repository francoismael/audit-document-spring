package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteInterviewUseCase;
import com.audit.audit_document.domain.entity.Interview;
import com.audit.audit_document.domain.repository.InterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteInterviewService implements DeleteInterviewUseCase {

    private final InterviewRepository interviewRepository;

    public DeleteInterviewService(
            InterviewRepository interviewRepository) {

        this.interviewRepository = interviewRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        // Check that the interview exists
        interviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Interview introuvable"));

        // Delete the interview
        interviewRepository.deleteById(id);
    }
}