package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentFieldResponse;

import java.util.List;

public interface GetDocumentFieldsUseCase {

    List<DocumentFieldResponse> getByDocumentType(
            Long documentTypeId);
}