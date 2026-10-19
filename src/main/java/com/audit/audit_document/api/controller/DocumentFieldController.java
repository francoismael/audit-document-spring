package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateDocumentFieldRequest;
import com.audit.audit_document.application.dto.CreateDocumentFieldsRequest;
import com.audit.audit_document.application.dto.DocumentFieldResponse;
import com.audit.audit_document.application.usecases.CreateDocumentFieldUseCase;
import com.audit.audit_document.application.usecases.CreateDocumentFieldsUseCase;
import com.audit.audit_document.application.usecases.DeleteDocumentFieldUseCase;
import com.audit.audit_document.application.usecases.GetDocumentFieldByIdUseCase;
import com.audit.audit_document.application.usecases.GetDocumentFieldsUseCase;
import com.audit.audit_document.application.usecases.UpdateDocumentFieldUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-fields")
public class DocumentFieldController {

    private final CreateDocumentFieldUseCase
            createDocumentFieldUseCase;

    private final CreateDocumentFieldsUseCase
            createDocumentFieldsUseCase;

    private final GetDocumentFieldsUseCase
            getDocumentFieldsUseCase;

    private final GetDocumentFieldByIdUseCase
            getDocumentFieldByIdUseCase;

    private final UpdateDocumentFieldUseCase
            updateDocumentFieldUseCase;

    private final DeleteDocumentFieldUseCase
            deleteDocumentFieldUseCase;

    public DocumentFieldController(
            CreateDocumentFieldUseCase createDocumentFieldUseCase,
            CreateDocumentFieldsUseCase createDocumentFieldsUseCase,
            GetDocumentFieldsUseCase getDocumentFieldsUseCase,
            GetDocumentFieldByIdUseCase getDocumentFieldByIdUseCase,
            UpdateDocumentFieldUseCase updateDocumentFieldUseCase,
            DeleteDocumentFieldUseCase deleteDocumentFieldUseCase) {

        this.createDocumentFieldUseCase =
                createDocumentFieldUseCase;

        this.createDocumentFieldsUseCase =
                createDocumentFieldsUseCase;

        this.getDocumentFieldsUseCase =
                getDocumentFieldsUseCase;

        this.getDocumentFieldByIdUseCase =
                getDocumentFieldByIdUseCase;

        this.updateDocumentFieldUseCase =
                updateDocumentFieldUseCase;

        this.deleteDocumentFieldUseCase =
                deleteDocumentFieldUseCase;
    }


    @PostMapping
    public ResponseEntity<DocumentFieldResponse> create(
            @RequestBody CreateDocumentFieldRequest request) {

        DocumentFieldResponse response =
                createDocumentFieldUseCase.create(
                        request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/batch")
    public ResponseEntity<List<DocumentFieldResponse>> createBatch(
            @RequestBody CreateDocumentFieldsRequest request) {

        List<DocumentFieldResponse> response =
                createDocumentFieldsUseCase.create(
                        request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/document-type/{documentTypeId}")
    public ResponseEntity<List<DocumentFieldResponse>>
            getByDocumentType(
                    @PathVariable Long documentTypeId) {

        return ResponseEntity.ok(
                getDocumentFieldsUseCase.getByDocumentType(
                        documentTypeId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<DocumentFieldResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                getDocumentFieldByIdUseCase.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<DocumentFieldResponse> update(
            @PathVariable Long id,
            @RequestBody CreateDocumentFieldRequest request) {

        return ResponseEntity.ok(
                updateDocumentFieldUseCase.update(
                        id,
                        request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteDocumentFieldUseCase.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}