package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreatePersonneRequest;
import com.audit.audit_document.domain.entity.Personne;

public interface CreatePersonneUseCase {
    Personne execute(CreatePersonneRequest request);
}
