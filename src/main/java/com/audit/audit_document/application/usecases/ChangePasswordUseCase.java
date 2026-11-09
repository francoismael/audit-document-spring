package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.ChangePasswordRequest;

public interface ChangePasswordUseCase {

    void changePassword(ChangePasswordRequest request);
}
