package com.audit.audit_document.application.usecases;

import com.audit.audit_document.domain.entity.Structure;

import java.util.List;

public interface GetAllStructuresUseCase {

    List<Structure> execute();
}