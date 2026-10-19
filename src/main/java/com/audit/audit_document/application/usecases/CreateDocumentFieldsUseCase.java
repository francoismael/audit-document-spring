package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateDocumentFieldsRequest;
import com.audit.audit_document.application.dto.DocumentFieldResponse;

import java.util.List;

public interface CreateDocumentFieldsUseCase {

    List<DocumentFieldResponse> create(
            CreateDocumentFieldsRequest request);
}