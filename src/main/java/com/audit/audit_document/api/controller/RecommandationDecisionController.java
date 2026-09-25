package com.audit.audit_document.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.audit_document.application.dto.DecisionMaintienRecommandationRequest;
import com.audit.audit_document.application.dto.RecommandationResponse;
import com.audit.audit_document.application.usecases.DeciderMaintienRecommandationUseCase;

@RestController
@RequestMapping("/api/recommandations")
public class RecommandationDecisionController {

    private final DeciderMaintienRecommandationUseCase
            deciderMaintienRecommandationUseCase;

    public RecommandationDecisionController(
            DeciderMaintienRecommandationUseCase
                    deciderMaintienRecommandationUseCase) {

        this.deciderMaintienRecommandationUseCase =
                deciderMaintienRecommandationUseCase;
    }

    @PutMapping("/{id}/maintien")
    public ResponseEntity<RecommandationResponse> decider(
            @PathVariable Long id,
            @RequestBody
                    DecisionMaintienRecommandationRequest request) {

        return ResponseEntity.ok(
                deciderMaintienRecommandationUseCase.decider(
                        id,
                        request
                )
        );
    }
}