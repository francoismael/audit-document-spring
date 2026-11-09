package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.UpdateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;

public interface UpdateUtilisateurUseCase {

    UtilisateurResponse update(Long id, UpdateUtilisateurRequest request);
}