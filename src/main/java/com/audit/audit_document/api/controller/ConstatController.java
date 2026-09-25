package com.audit.audit_document.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.audit_document.application.dto.ConstatResponse;
import com.audit.audit_document.application.dto.CreateConstatRequest;
import com.audit.audit_document.application.usecases.CreateConstatUseCase;
import com.audit.audit_document.application.usecases.DeleteConstatUseCase;
import com.audit.audit_document.application.usecases.GetAllConstatsUseCase;
import com.audit.audit_document.application.usecases.GetConstatByIdUseCase;
import com.audit.audit_document.application.usecases.GetConstatsByTestUseCase;
import com.audit.audit_document.application.usecases.UpdateConstatUseCase;

@RestController
@RequestMapping("/api/constats")
public class ConstatController {

    private final CreateConstatUseCase createConstatUseCase;
    private final GetConstatByIdUseCase getConstatByIdUseCase;
    private final GetAllConstatsUseCase getAllConstatsUseCase;
    private final GetConstatsByTestUseCase getConstatsByTestUseCase;
    private final UpdateConstatUseCase updateConstatUseCase;
    private final DeleteConstatUseCase deleteConstatUseCase;

    public ConstatController(
            CreateConstatUseCase createConstatUseCase,
            GetConstatByIdUseCase getConstatByIdUseCase,
            GetAllConstatsUseCase getAllConstatsUseCase,
            GetConstatsByTestUseCase getConstatsByTestUseCase,
            UpdateConstatUseCase updateConstatUseCase,
            DeleteConstatUseCase deleteConstatUseCase) {

        this.createConstatUseCase = createConstatUseCase;
        this.getConstatByIdUseCase = getConstatByIdUseCase;
        this.getAllConstatsUseCase = getAllConstatsUseCase;
        this.getConstatsByTestUseCase = getConstatsByTestUseCase;
        this.updateConstatUseCase = updateConstatUseCase;
        this.deleteConstatUseCase = deleteConstatUseCase;
    }

    @PostMapping
    public ResponseEntity<ConstatResponse> create(
            @RequestBody CreateConstatRequest request) {

        ConstatResponse response =
                createConstatUseCase.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<ConstatResponse>> getAll() {

        return ResponseEntity.ok(
                getAllConstatsUseCase.getAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ConstatResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                getConstatByIdUseCase.getById(id)
        );
    }


    @GetMapping("/test/{testId}")
    public ResponseEntity<List<ConstatResponse>> getByTest(
            @PathVariable Long testId) {

        return ResponseEntity.ok(
                getConstatsByTestUseCase.getByTestId(testId)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<ConstatResponse> update(
            @PathVariable Long id,
            @RequestBody CreateConstatRequest request) {

        return ResponseEntity.ok(
                updateConstatUseCase.update(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteConstatUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}