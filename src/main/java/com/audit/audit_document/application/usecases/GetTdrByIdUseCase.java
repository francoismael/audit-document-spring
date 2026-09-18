package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.TdrResponse;

public interface GetTdrByIdUseCase {

    TdrResponse execute(Long id);
}