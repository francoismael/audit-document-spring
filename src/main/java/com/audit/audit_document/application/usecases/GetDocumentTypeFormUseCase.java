package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentTypeFormResponse;

public interface GetDocumentTypeFormUseCase {

    DocumentTypeFormResponse getForm(
            Long documentTypeId);
}