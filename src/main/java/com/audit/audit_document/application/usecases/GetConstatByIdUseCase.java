package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ConstatResponse;

public interface GetConstatByIdUseCase {

    ConstatResponse getById(Long id);
}