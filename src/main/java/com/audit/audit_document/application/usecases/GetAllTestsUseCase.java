package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.TestResponse;

import java.util.List;

public interface GetAllTestsUseCase {

    List<TestResponse> getAll();
}