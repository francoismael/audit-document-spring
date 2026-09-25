package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.DecisionMaintienRecommandationRequest;
import com.audit.audit_document.application.dto.RecommandationResponse;

public interface DeciderMaintienRecommandationUseCase {

    RecommandationResponse decider(
            Long recommandationId,
            DecisionMaintienRecommandationRequest request
    );
}