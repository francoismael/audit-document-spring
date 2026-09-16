package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateStructureRequest;
import com.audit.audit_document.domain.entity.Structure;

public interface CreateStructureUseCase {

    Structure execute(CreateStructureRequest request);
}