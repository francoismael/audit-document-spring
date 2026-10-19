package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.GeneratedDocument;

import java.util.Map;

public interface GenerateDocumentUseCase {

    GeneratedDocument generate(
            Long documentTypeId,
            Map<String, Object> values,
            Long missionId);
}
