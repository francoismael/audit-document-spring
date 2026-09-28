package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateRapportAuditRequest;
import com.audit.audit_document.application.dto.RapportAuditResponse;
import com.audit.audit_document.application.usecases.CreateRapportAuditUseCase;
import com.audit.audit_document.application.usecases.GetRapportAuditByMissionUseCase;
import com.audit.audit_document.application.usecases.UpdateRapportAuditUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rapports")
@CrossOrigin(origins = "*")
public class RapportAuditController {

    private final CreateRapportAuditUseCase createUseCase;
    private final GetRapportAuditByMissionUseCase getByMissionUseCase;
    private final UpdateRapportAuditUseCase updateUseCase;

    public RapportAuditController(
            CreateRapportAuditUseCase createUseCase,
            GetRapportAuditByMissionUseCase getByMissionUseCase,
            UpdateRapportAuditUseCase updateUseCase) {

        this.createUseCase = createUseCase;
        this.getByMissionUseCase = getByMissionUseCase;
        this.updateUseCase = updateUseCase;
    }


    @PostMapping
    public ResponseEntity<RapportAuditResponse> create(
            @RequestBody CreateRapportAuditRequest request) {

        RapportAuditResponse response =
                createUseCase.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/mission/{missionId}")
    public ResponseEntity<RapportAuditResponse> getByMissionId(
            @PathVariable Long missionId) {

        RapportAuditResponse response =
                getByMissionUseCase.getByMissionId(missionId);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RapportAuditResponse> update(
            @PathVariable Long id,
            @RequestBody CreateRapportAuditRequest request) {

        RapportAuditResponse response =
                updateUseCase.update(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }
}