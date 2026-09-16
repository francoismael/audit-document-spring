package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.UtilisateurResponse;

import java.util.List;

public interface GetAllUtilisateursUseCase {

    List<UtilisateurResponse> execute();
}