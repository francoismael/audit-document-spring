package com.audit.audit_document.application.usecases;

import java.util.List;

import com.audit.audit_document.application.dto.ConstatResponse;

public interface GetConstatsByTestUseCase {

    List<ConstatResponse> getByTestId(Long testId);
}