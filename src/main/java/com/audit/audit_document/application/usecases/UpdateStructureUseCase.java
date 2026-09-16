package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.UpdateStructureRequest;
import com.audit.audit_document.domain.entity.Structure;

public interface UpdateStructureUseCase {

    Structure execute(Long id, UpdateStructureRequest request);
}