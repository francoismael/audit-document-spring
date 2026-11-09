package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DocumentGenereResponse;

import java.util.List;

public interface GetDocumentsGeneresUseCase {

    // query: file name, document type or mission (optional).
    List<DocumentGenereResponse> getAll(
            Long documentTypeId,
            Long missionId,
            String query);
}
