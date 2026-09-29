package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreatePvAffirmationRequest;
import com.audit.audit_document.application.dto.PvAffirmationResponse;
import com.audit.audit_document.application.usecases.CreatePvAffirmationUseCase;
import com.audit.audit_document.application.usecases.DeletePvAffirmationUseCase;
import com.audit.audit_document.application.usecases.GetPvAffirmationByIdUseCase;
import com.audit.audit_document.application.usecases.GetPvAffirmationByMissionUseCase;
import com.audit.audit_document.application.usecases.UpdatePvAffirmationUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pv-affirmations")
@CrossOrigin(origins = "*")
public class PvAffirmationController {

    private final CreatePvAffirmationUseCase createUseCase;
    private final GetPvAffirmationByIdUseCase getByIdUseCase;
    private final GetPvAffirmationByMissionUseCase
            getByMissionUseCase;
    private final UpdatePvAffirmationUseCase updateUseCase;
    private final DeletePvAffirmationUseCase deleteUseCase;

    public PvAffirmationController(
            CreatePvAffirmationUseCase createUseCase,
            GetPvAffirmationByIdUseCase getByIdUseCase,
            GetPvAffirmationByMissionUseCase
                    getByMissionUseCase,
            UpdatePvAffirmationUseCase updateUseCase,
            DeletePvAffirmationUseCase deleteUseCase) {

        this.createUseCase = createUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.getByMissionUseCase =
                getByMissionUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }



    @PostMapping
    public ResponseEntity<PvAffirmationResponse> create(
            @RequestBody CreatePvAffirmationRequest request) {

        PvAffirmationResponse response =
                createUseCase.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<PvAffirmationResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                getByIdUseCase.getById(id)
        );
    }


    @GetMapping("/mission/{missionId}")
    public ResponseEntity<PvAffirmationResponse>
    getByMissionId(
            @PathVariable Long missionId) {

        return ResponseEntity.ok(
                getByMissionUseCase.getByMissionId(
                        missionId
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<PvAffirmationResponse> update(
            @PathVariable Long id,
            @RequestBody CreatePvAffirmationRequest request) {

        PvAffirmationResponse response =
                updateUseCase.update(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}