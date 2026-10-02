package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.AvancementMissionResponse;
import com.audit.audit_document.application.usecases.GetAvancementMissionsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistiques")
public class AvancementMissionsController {

    private final GetAvancementMissionsUseCase
            getAvancementMissionsUseCase;

    public AvancementMissionsController(
            GetAvancementMissionsUseCase
                    getAvancementMissionsUseCase) {

        this.getAvancementMissionsUseCase =
                getAvancementMissionsUseCase;
    }

    @GetMapping("/avancement")
    public ResponseEntity<List<AvancementMissionResponse>>
            getAvancementMissions() {

        return ResponseEntity.ok(
                getAvancementMissionsUseCase.execute()
        );
    }
}