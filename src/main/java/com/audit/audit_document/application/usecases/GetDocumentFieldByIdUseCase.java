package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentFieldResponse;

public interface GetDocumentFieldByIdUseCase {

    DocumentFieldResponse getById(Long id);
}