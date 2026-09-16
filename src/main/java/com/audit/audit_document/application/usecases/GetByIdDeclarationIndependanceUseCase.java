package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;

public interface GetByIdDeclarationIndependanceUseCase {
    DeclarationIndependanceResponse execute(Long id);
}
