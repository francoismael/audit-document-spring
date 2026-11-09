package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.OrdreSectionsRequest;
import com.audit.audit_document.application.dto.OrdreSectionsResponse;
import com.audit.audit_document.application.usecases.GetOrdreSectionsUseCase;
import com.audit.audit_document.application.usecases.ResetOrdreSectionsUseCase;
import com.audit.audit_document.application.usecases.SaveOrdreSectionsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Order of the sections in the rendering of the business documents (Paramètres).
@RestController
@RequestMapping("/api/parametres/ordre-sections")
public class OrdreSectionController {

    private final GetOrdreSectionsUseCase getOrdreSectionsUseCase;
    private final SaveOrdreSectionsUseCase saveOrdreSectionsUseCase;
    private final ResetOrdreSectionsUseCase resetOrdreSectionsUseCase;

    public OrdreSectionController(
            GetOrdreSectionsUseCase getOrdreSectionsUseCase,
            SaveOrdreSectionsUseCase saveOrdreSectionsUseCase,
            ResetOrdreSectionsUseCase resetOrdreSectionsUseCase) {

        this.getOrdreSectionsUseCase = getOrdreSectionsUseCase;
        this.saveOrdreSectionsUseCase = saveOrdreSectionsUseCase;
        this.resetOrdreSectionsUseCase = resetOrdreSectionsUseCase;
    }

    @GetMapping
    public ResponseEntity<List<OrdreSectionsResponse>> getAll() {

        return ResponseEntity.ok(getOrdreSectionsUseCase.getAll());
    }

    @PutMapping("/{documentCode}")
    public ResponseEntity<OrdreSectionsResponse> save(
            @PathVariable String documentCode,
            @RequestBody OrdreSectionsRequest request) {

        return ResponseEntity.ok(saveOrdreSectionsUseCase.save(documentCode, request));
    }

    @DeleteMapping("/{documentCode}")
    public ResponseEntity<Void> reset(
            @PathVariable String documentCode) {

        resetOrdreSectionsUseCase.reset(documentCode);

        return ResponseEntity.noContent().build();
    }
}
