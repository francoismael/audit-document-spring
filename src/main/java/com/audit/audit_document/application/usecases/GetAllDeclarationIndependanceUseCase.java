package com.audit.audit_document.application.usecases;

import java.util.List;

import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;

public interface GetAllDeclarationIndependanceUseCase {

    List<DeclarationIndependanceResponse> execute();
}