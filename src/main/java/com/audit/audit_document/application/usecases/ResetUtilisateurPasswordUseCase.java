package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ResetPasswordRequest;

public interface ResetUtilisateurPasswordUseCase {

    void resetPassword(Long id, ResetPasswordRequest request);
}
