package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ProgrammeTravailResponse;

import java.util.List;

public interface GetAllProgrammesTravailUseCase {

    List<ProgrammeTravailResponse> getAll();
}