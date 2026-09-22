package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateProgrammeTravailRequest;
import com.audit.audit_document.application.dto.ProgrammeTravailResponse;

public interface CreateProgrammeTravailUseCase {

    ProgrammeTravailResponse create(CreateProgrammeTravailRequest request);
}