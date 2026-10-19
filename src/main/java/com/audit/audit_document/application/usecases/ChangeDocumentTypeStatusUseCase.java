package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentTypeResponse;

public interface ChangeDocumentTypeStatusUseCase {

    DocumentTypeResponse changeStatus(
            Long id,
            Boolean actif);
}
