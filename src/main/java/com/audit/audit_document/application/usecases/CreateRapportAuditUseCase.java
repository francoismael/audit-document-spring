package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateRapportAuditRequest;
import com.audit.audit_document.application.dto.RapportAuditResponse;

public interface CreateRapportAuditUseCase {

    RapportAuditResponse create(
            CreateRapportAuditRequest request
    );
}