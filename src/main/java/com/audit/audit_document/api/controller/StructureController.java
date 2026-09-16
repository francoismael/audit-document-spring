package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateStructureRequest;
import com.audit.audit_document.application.dto.UpdateStructureRequest;
import com.audit.audit_document.application.usecases.CreateStructureUseCase;
import com.audit.audit_document.application.usecases.DeleteStructureUseCase;
import com.audit.audit_document.application.usecases.GetAllStructuresUseCase;
import com.audit.audit_document.application.usecases.GetStructureByIdUseCase;
import com.audit.audit_document.application.usecases.UpdateStructureUseCase;
import com.audit.audit_document.domain.entity.Structure;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/structures")
public class StructureController {

    private final CreateStructureUseCase createStructureUseCase;
    private final GetAllStructuresUseCase getAllStructuresUseCase;
    private final GetStructureByIdUseCase getStructureByIdUseCase;
    private final UpdateStructureUseCase updateStructureUseCase;
    private final DeleteStructureUseCase deleteStructureUseCase;

    public StructureController(
        CreateStructureUseCase createStructureUseCase,
        UpdateStructureUseCase updateStructureUseCase,
        DeleteStructureUseCase deleteStructureUseCase,
        GetAllStructuresUseCase getAllStructuresUseCase,
        GetStructureByIdUseCase getStructureByIdUseCase
    ) {
    this.createStructureUseCase = createStructureUseCase;
    this.getAllStructuresUseCase = getAllStructuresUseCase;
    this.getStructureByIdUseCase = getStructureByIdUseCase;
    this.updateStructureUseCase = updateStructureUseCase;
    this.deleteStructureUseCase = deleteStructureUseCase;
    }

    @PostMapping
    public ResponseEntity<Structure> create(
            @RequestBody CreateStructureRequest request
    ) {
        Structure structure =
                createStructureUseCase.execute(request);

        return ResponseEntity.ok(structure);
    }

    @GetMapping
    public ResponseEntity<List<Structure>> getAll() {

    List<Structure> structures =
            getAllStructuresUseCase.execute();

    return ResponseEntity.ok(structures);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Structure> getById(
        @PathVariable Long id
    ) {
    Structure structure =
            getStructureByIdUseCase.execute(id);

    return ResponseEntity.ok(structure);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Structure> update(
        @PathVariable Long id,
        @RequestBody UpdateStructureRequest request
    ) {
    Structure structure =
            updateStructureUseCase.execute(id, request);

    return ResponseEntity.ok(structure);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id
    ) {
    deleteStructureUseCase.execute(id);

    return ResponseEntity.noContent().build();
    }
}