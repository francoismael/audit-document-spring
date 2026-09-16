package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.UtilisateurResponse;

public interface GetUtilisateurByIdUseCase {

    UtilisateurResponse execute(Long id);
}