package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.PvAffirmationResponse;

public interface GetPvAffirmationByIdUseCase {

    PvAffirmationResponse getById(Long id);
}