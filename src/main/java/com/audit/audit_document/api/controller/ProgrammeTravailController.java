package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateProgrammeTravailRequest;
import com.audit.audit_document.application.dto.ProgrammeTravailResponse;
import com.audit.audit_document.application.usecases.CreateProgrammeTravailUseCase;
import com.audit.audit_document.application.usecases.DeleteProgrammeTravailUseCase;
import com.audit.audit_document.application.usecases.GetAllProgrammesTravailUseCase;
import com.audit.audit_document.application.usecases.GetProgrammeTravailByIdUseCase;
import com.audit.audit_document.application.usecases.UpdateProgrammeTravailUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programmes-travail")
public class ProgrammeTravailController {

    private final CreateProgrammeTravailUseCase createProgrammeTravailUseCase;
    private final GetProgrammeTravailByIdUseCase getProgrammeTravailByIdUseCase;
    private final GetAllProgrammesTravailUseCase getAllProgrammesTravailUseCase;
    private final UpdateProgrammeTravailUseCase updateProgrammeTravailUseCase;
    private final DeleteProgrammeTravailUseCase deleteProgrammeTravailUseCase;

    public ProgrammeTravailController(
            CreateProgrammeTravailUseCase createProgrammeTravailUseCase,
            GetProgrammeTravailByIdUseCase getProgrammeTravailByIdUseCase,
            GetAllProgrammesTravailUseCase getAllProgrammesTravailUseCase,
            UpdateProgrammeTravailUseCase updateProgrammeTravailUseCase,
            DeleteProgrammeTravailUseCase deleteProgrammeTravailUseCase) {

        this.createProgrammeTravailUseCase = createProgrammeTravailUseCase;
        this.getProgrammeTravailByIdUseCase = getProgrammeTravailByIdUseCase;
        this.getAllProgrammesTravailUseCase = getAllProgrammesTravailUseCase;
        this.updateProgrammeTravailUseCase = updateProgrammeTravailUseCase;
        this.deleteProgrammeTravailUseCase = deleteProgrammeTravailUseCase;
    }


    @PostMapping
    public ResponseEntity<ProgrammeTravailResponse> create(
            @RequestBody CreateProgrammeTravailRequest request) {

        ProgrammeTravailResponse response =
                createProgrammeTravailUseCase.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProgrammeTravailResponse> getById(
            @PathVariable Long id) {

        ProgrammeTravailResponse response =
                getProgrammeTravailByIdUseCase.getById(id);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<ProgrammeTravailResponse>> getAll() {

        List<ProgrammeTravailResponse> responses =
                getAllProgrammesTravailUseCase.getAll();

        return ResponseEntity.ok(responses);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProgrammeTravailResponse> update(
            @PathVariable Long id,
            @RequestBody CreateProgrammeTravailRequest request) {

        ProgrammeTravailResponse response =
                updateProgrammeTravailUseCase.update(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteProgrammeTravailUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}