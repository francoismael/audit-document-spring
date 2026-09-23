package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateTestRequest;
import com.audit.audit_document.application.dto.TestResponse;

public interface CreateTestUseCase {

    TestResponse create(CreateTestRequest request);
}