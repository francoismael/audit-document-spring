package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateInterviewRequest;
import com.audit.audit_document.application.dto.InterviewResponse;

public interface UpdateInterviewUseCase {

    InterviewResponse execute(Long id, CreateInterviewRequest request);
}