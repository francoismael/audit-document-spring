package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentGenereResponse;

public interface GetDocumentGenereByIdUseCase {

    DocumentGenereResponse getById(Long id);
}
