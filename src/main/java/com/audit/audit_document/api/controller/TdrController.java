package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateTdrRequest;
import com.audit.audit_document.application.dto.TdrResponse;
import com.audit.audit_document.application.usecases.CreateTdrUseCase;
import com.audit.audit_document.application.usecases.DeleteTdrUseCase;
import com.audit.audit_document.application.usecases.GetAllTdrsUseCase;
import com.audit.audit_document.application.usecases.GetTdrByIdUseCase;
import com.audit.audit_document.application.usecases.UpdateTdrUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tdrs")
public class TdrController {

    private final CreateTdrUseCase createTdrUseCase;
    private final GetTdrByIdUseCase getTdrByIdUseCase;
    private final GetAllTdrsUseCase getAllTdrsUseCase;
    private final UpdateTdrUseCase updateTdrUseCase;
    private final DeleteTdrUseCase deleteTdrUseCase;

    public TdrController(
            CreateTdrUseCase createTdrUseCase,
            GetTdrByIdUseCase getTdrByIdUseCase,
            GetAllTdrsUseCase getAllTdrsUseCase,
            UpdateTdrUseCase updateTdrUseCase,
            DeleteTdrUseCase deleteTdrUseCase) {

        this.createTdrUseCase = createTdrUseCase;
        this.getTdrByIdUseCase = getTdrByIdUseCase;
        this.getAllTdrsUseCase = getAllTdrsUseCase;
        this.updateTdrUseCase = updateTdrUseCase;
        this.deleteTdrUseCase = deleteTdrUseCase;
    }

    @PostMapping
    public ResponseEntity<TdrResponse> create(
            @RequestBody CreateTdrRequest request) {

        TdrResponse response = createTdrUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TdrResponse>> getAll() {

        List<TdrResponse> responses = getAllTdrsUseCase.execute();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TdrResponse> getById(
            @PathVariable Long id) {

        TdrResponse response = getTdrByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TdrResponse> update(
            @PathVariable Long id,
            @RequestBody CreateTdrRequest request) {

        TdrResponse response = updateTdrUseCase.execute(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteTdrUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}