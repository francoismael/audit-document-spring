package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateReunionRequest;
import com.audit.audit_document.application.dto.ReunionResponse;

public interface UpdateReunionUseCase {

    ReunionResponse execute(
            Long id,
            CreateReunionRequest request);
}