package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;

public interface CreateUtilisateurUseCase {

    UtilisateurResponse execute(CreateUtilisateurRequest request);
}