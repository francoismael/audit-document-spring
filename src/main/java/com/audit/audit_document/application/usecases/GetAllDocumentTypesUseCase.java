package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentTypeResponse;

import java.util.List;

public interface GetAllDocumentTypesUseCase {

    // query: nom, code or description (optional); actif: only the active or inactive types (optional);
    // genere: only the types that already generated a document, or the ones that never did (optional).
    List<DocumentTypeResponse> execute(String query, Boolean actif, Boolean genere);
}
