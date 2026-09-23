package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.TestResponse;

public interface GetTestByIdUseCase {

    TestResponse getById(Long id);
}