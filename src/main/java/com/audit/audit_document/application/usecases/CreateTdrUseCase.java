package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateTdrRequest;
import com.audit.audit_document.application.dto.TdrResponse;

public interface CreateTdrUseCase {

    TdrResponse execute(CreateTdrRequest request);
}