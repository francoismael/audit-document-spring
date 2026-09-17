package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ReunionResponse;

public interface GetReunionByIdUseCase {

    ReunionResponse execute(Long id);
}