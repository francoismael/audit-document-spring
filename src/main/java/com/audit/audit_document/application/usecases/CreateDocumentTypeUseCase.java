package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateDocumentTypeRequest;
import com.audit.audit_document.application.dto.DocumentTypeResponse;

public interface CreateDocumentTypeUseCase {

    DocumentTypeResponse execute(
            CreateDocumentTypeRequest request);
}