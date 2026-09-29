package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreatePvAffirmationRequest;
import com.audit.audit_document.application.dto.PvAffirmationResponse;

public interface CreatePvAffirmationUseCase {

    PvAffirmationResponse create(
            CreatePvAffirmationRequest request
    );
}