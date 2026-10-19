package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentTemplateValidationResponse;

public interface ValidateDocumentTemplateVariablesUseCase {

    DocumentTemplateValidationResponse execute(
            Long documentTypeId);
}