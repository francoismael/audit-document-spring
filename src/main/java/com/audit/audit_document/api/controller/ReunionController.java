package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateReunionRequest;
import com.audit.audit_document.application.dto.ReunionResponse;
import com.audit.audit_document.application.usecases.CreateReunionUseCase;
import com.audit.audit_document.application.usecases.DeleteReunionUseCase;
import com.audit.audit_document.application.usecases.GetAllReunionsUseCase;
import com.audit.audit_document.application.usecases.GetReunionByIdUseCase;
import com.audit.audit_document.application.usecases.UpdateReunionUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunions")
public class ReunionController {

    private final CreateReunionUseCase createReunionUseCase;
    private final GetReunionByIdUseCase getReunionByIdUseCase;
    private final GetAllReunionsUseCase getAllReunionsUseCase;
    private final UpdateReunionUseCase updateReunionUseCase;
    private final DeleteReunionUseCase deleteReunionUseCase;

    public ReunionController(
            CreateReunionUseCase createReunionUseCase,
            GetReunionByIdUseCase getReunionByIdUseCase,
            GetAllReunionsUseCase getAllReunionsUseCase,
            UpdateReunionUseCase updateReunionUseCase,
            DeleteReunionUseCase deleteReunionUseCase) {

        this.createReunionUseCase = createReunionUseCase;
        this.getReunionByIdUseCase = getReunionByIdUseCase;
        this.getAllReunionsUseCase = getAllReunionsUseCase;
        this.updateReunionUseCase = updateReunionUseCase;
        this.deleteReunionUseCase = deleteReunionUseCase;
    }

    @PostMapping
    public ResponseEntity<ReunionResponse> create(
            @RequestBody CreateReunionRequest request) {

        ReunionResponse response =
                createReunionUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReunionResponse>> getAll() {

        return ResponseEntity.ok(
                getAllReunionsUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReunionResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                getReunionByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReunionResponse> update(
            @PathVariable Long id,
            @RequestBody CreateReunionRequest request) {

        return ResponseEntity.ok(
                updateReunionUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteReunionUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}