package com.audit.audit_document.application.usecases;

import com.audit.audit_document.domain.entity.Personne;

public interface GetPersonneByIdUseCase {

    Personne execute(Long id);
}