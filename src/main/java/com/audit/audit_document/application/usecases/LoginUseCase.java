package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.LoginRequest;
import com.audit.audit_document.application.dto.LoginResponse;

public interface LoginUseCase {

    LoginResponse execute(LoginRequest request);
}