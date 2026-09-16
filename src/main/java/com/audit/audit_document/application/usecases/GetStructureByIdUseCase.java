package com.audit.audit_document.application.usecases;

import com.audit.audit_document.domain.entity.Structure;

public interface GetStructureByIdUseCase {

    Structure execute(Long id);
}