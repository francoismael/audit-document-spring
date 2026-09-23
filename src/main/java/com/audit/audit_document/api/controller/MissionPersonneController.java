package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.MissionPersonneResponse;
import com.audit.audit_document.application.usecases.GetMissionPersonnesByMissionUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mission-personnes")
public class MissionPersonneController {

    private final GetMissionPersonnesByMissionUseCase
            getMissionPersonnesByMissionUseCase;

    public MissionPersonneController(
            GetMissionPersonnesByMissionUseCase
                    getMissionPersonnesByMissionUseCase) {

        this.getMissionPersonnesByMissionUseCase =
                getMissionPersonnesByMissionUseCase;
    }

    @GetMapping("/mission/{missionId}")
    public ResponseEntity<List<MissionPersonneResponse>>
    getByMissionId(
            @PathVariable Long missionId) {

        List<MissionPersonneResponse> responses =
                getMissionPersonnesByMissionUseCase
                        .getByMissionId(missionId);

        return ResponseEntity.ok(responses);
    }
}