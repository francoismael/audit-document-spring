package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ProgrammeTravailResponse;

public interface GetProgrammeTravailByIdUseCase {

    ProgrammeTravailResponse getById(Long id);
}