package com.audit.audit_document.api.controller;
import java.util.List;
import com.audit.audit_document.application.dto.CreateDeclarationIndependanceRequest;
import com.audit.audit_document.application.dto.DeclarationIndependanceResponse;
import com.audit.audit_document.application.usecases.CreateDeclarationIndependanceUseCase;
import com.audit.audit_document.application.usecases.DeleteDeclarationIndependanceUseCase;
import com.audit.audit_document.application.usecases.GetAllDeclarationIndependanceUseCase;
import com.audit.audit_document.application.usecases.GetByIdDeclarationIndependanceUseCase;
import com.audit.audit_document.application.usecases.UpdateDeclarationIndependanceUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/declarations-independance")
public class DeclarationIndependanceController {

    private final CreateDeclarationIndependanceUseCase createUseCase;
    private final GetByIdDeclarationIndependanceUseCase getUseCase;
    private final UpdateDeclarationIndependanceUseCase updateUseCase;
    private final DeleteDeclarationIndependanceUseCase deleteUseCase;
    private final GetAllDeclarationIndependanceUseCase getAllUseCase;

    public DeclarationIndependanceController(
            CreateDeclarationIndependanceUseCase createUseCase,
            UpdateDeclarationIndependanceUseCase updateUseCase,
            GetByIdDeclarationIndependanceUseCase getUseCase,
            DeleteDeclarationIndependanceUseCase deleteUseCase,
            GetAllDeclarationIndependanceUseCase getAllUseCase) {

        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
            this.getAllUseCase = getAllUseCase;
            this.updateUseCase = updateUseCase;
            this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<DeclarationIndependanceResponse> create(
            @RequestBody CreateDeclarationIndependanceRequest request) {

        DeclarationIndependanceResponse response =
                createUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeclarationIndependanceResponse> getById(
            @PathVariable Long id) {

        DeclarationIndependanceResponse response =
                getUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DeclarationIndependanceResponse>> getAll() {

    List<DeclarationIndependanceResponse> responses =
            getAllUseCase.execute();

    return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeclarationIndependanceResponse> update(
        @PathVariable Long id,
        @RequestBody CreateDeclarationIndependanceRequest request) {

    DeclarationIndependanceResponse response =
            updateUseCase.execute(id, request);

    return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

    deleteUseCase.execute(id);

    return ResponseEntity.noContent().build();
    }

}