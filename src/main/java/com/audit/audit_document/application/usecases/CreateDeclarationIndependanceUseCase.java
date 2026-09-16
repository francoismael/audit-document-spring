package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateDeclarationIndependanceRequest;
import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;


public interface CreateDeclarationIndependanceUseCase {
    DeclarationIndependanceResponse execute(CreateDeclarationIndependanceRequest request);
}
