package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ReunionResponse;

import java.util.List;

public interface GetAllReunionsUseCase {

    List<ReunionResponse> execute();
}