package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateReponseRequest;
import com.audit.audit_document.application.dto.ReponseResponse;

public interface UpdateReponseUseCase {

    ReponseResponse update(
            Long id,
            CreateReponseRequest request
    );
}