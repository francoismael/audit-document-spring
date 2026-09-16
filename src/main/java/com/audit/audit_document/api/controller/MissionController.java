package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.application.dto.MissionResponse;
import com.audit.audit_document.application.usecases.CreateMissionUseCase;
import com.audit.audit_document.application.usecases.DeleteMissionUseCase;
import com.audit.audit_document.application.usecases.GetAllMissionUseCase;
import com.audit.audit_document.application.usecases.GetByIdMissionUseCase;
import com.audit.audit_document.application.usecases.UpdateMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final CreateMissionUseCase createMissionUseCase;
    private final GetByIdMissionUseCase getMissionUseCase;
    private final UpdateMissionUseCase updateMissionUseCase;
    private final DeleteMissionUseCase deleteMissionUseCase;
    private final GetAllMissionUseCase getAllMissionUseCase;

    public MissionController(
            CreateMissionUseCase createMissionUseCase,
             GetAllMissionUseCase getAllMissionUseCase,
             DeleteMissionUseCase deleteMissionUseCase,
             UpdateMissionUseCase updateMissionUseCase,
            GetByIdMissionUseCase getMissionUseCase) {

        this.createMissionUseCase = createMissionUseCase;
        this.getMissionUseCase = getMissionUseCase;
        this.getAllMissionUseCase = getAllMissionUseCase;
        this.updateMissionUseCase = updateMissionUseCase;
        this.deleteMissionUseCase = deleteMissionUseCase;
    }

    @PostMapping
    public ResponseEntity<Mission> create(
            @RequestBody CreateMissionRequest request) {

        Mission mission = createMissionUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mission);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissionResponse> getById(
            @PathVariable Long id) {

        MissionResponse mission = getMissionUseCase.execute(id);

        return ResponseEntity.ok(mission);
    }

    @GetMapping
    public ResponseEntity<List<MissionResponse>> getAll() {

    List<MissionResponse> missions =
            getAllMissionUseCase.execute();

    return ResponseEntity.ok(missions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissionResponse> update(
        @PathVariable Long id,
        @RequestBody CreateMissionRequest request) {

    MissionResponse mission =
            updateMissionUseCase.execute(id, request);

    return ResponseEntity.ok(mission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id) {

    deleteMissionUseCase.execute(id);

    return ResponseEntity.noContent().build();
    }
}