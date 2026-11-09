package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.RegisterRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;

public interface RegisterUseCase {

    UtilisateurResponse register(RegisterRequest request);
}
