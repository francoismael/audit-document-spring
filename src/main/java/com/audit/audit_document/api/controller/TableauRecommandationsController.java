package com.audit.audit_document.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.audit_document.application.dto.TableauRecommandationsResponse;
import com.audit.audit_document.application.usecases.GetTableauRecommandationsUseCase;

@RestController
@RequestMapping("/api/tableau-recommandations")
public class TableauRecommandationsController {

    private final GetTableauRecommandationsUseCase
            getTableauRecommandationsUseCase;

    public TableauRecommandationsController(
            GetTableauRecommandationsUseCase
                    getTableauRecommandationsUseCase) {

        this.getTableauRecommandationsUseCase =
                getTableauRecommandationsUseCase;
    }

    @GetMapping("/mission/{missionId}")
    public ResponseEntity<TableauRecommandationsResponse>
            getByMissionId(
                    @PathVariable Long missionId) {

        return ResponseEntity.ok(
                getTableauRecommandationsUseCase
                        .getByMissionId(missionId)
        );
    }
}