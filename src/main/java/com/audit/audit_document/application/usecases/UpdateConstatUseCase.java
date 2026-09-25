package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ConstatResponse;
import com.audit.audit_document.application.dto.CreateConstatRequest;

public interface UpdateConstatUseCase {

    ConstatResponse update(Long id, CreateConstatRequest request);
}