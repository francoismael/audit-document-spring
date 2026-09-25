package com.audit.audit_document.application.usecases;

import java.util.List;

import com.audit.audit_document.application.dto.ReponseResponse;

public interface GetReponsesByRecommandationUseCase {

    List<ReponseResponse> getByRecommandationId(
            Long recommandationId
    );
}