package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateDocumentFieldRequest;
import com.audit.audit_document.application.dto.DocumentFieldResponse;

public interface CreateDocumentFieldUseCase {

    DocumentFieldResponse create(
            CreateDocumentFieldRequest request);
}