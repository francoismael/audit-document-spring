package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateDocumentTypeRequest;
import com.audit.audit_document.application.dto.DocumentTemplateValidationResponse;
import com.audit.audit_document.application.dto.DocumentTypeFormResponse;
import com.audit.audit_document.application.dto.DocumentTypeResponse;
import com.audit.audit_document.application.dto.DocumentTypeStatusRequest;
import com.audit.audit_document.application.dto.GeneratedDocument;
import com.audit.audit_document.application.usecases.ChangeDocumentTypeStatusUseCase;
import com.audit.audit_document.application.usecases.CreateDocumentTypeUseCase;
import com.audit.audit_document.application.usecases.DeleteDocumentTypeUseCase;
import com.audit.audit_document.application.usecases.GenerateDocumentUseCase;
import com.audit.audit_document.application.usecases.GetAllDocumentTypesUseCase;
import com.audit.audit_document.application.usecases.GetDocumentTypeFormUseCase;
import com.audit.audit_document.application.usecases.UpdateDocumentTypeUseCase;
import com.audit.audit_document.application.usecases.ValidateDocumentTemplateVariablesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTypeController {

    private final CreateDocumentTypeUseCase
            createDocumentTypeUseCase;

    private final GetAllDocumentTypesUseCase
            getAllDocumentTypesUseCase;

    private final GetDocumentTypeFormUseCase
            getDocumentTypeFormUseCase;

    private final ValidateDocumentTemplateVariablesUseCase
            validateDocumentTemplateVariablesUseCase;

    private final GenerateDocumentUseCase
            generateDocumentUseCase;

    private final UpdateDocumentTypeUseCase
            updateDocumentTypeUseCase;

    private final ChangeDocumentTypeStatusUseCase
            changeDocumentTypeStatusUseCase;

    private final DeleteDocumentTypeUseCase
            deleteDocumentTypeUseCase;

    public DocumentTypeController(
            CreateDocumentTypeUseCase createDocumentTypeUseCase,
            GetAllDocumentTypesUseCase getAllDocumentTypesUseCase,
            GetDocumentTypeFormUseCase getDocumentTypeFormUseCase,
            ValidateDocumentTemplateVariablesUseCase
                    validateDocumentTemplateVariablesUseCase,
            GenerateDocumentUseCase generateDocumentUseCase,
            UpdateDocumentTypeUseCase updateDocumentTypeUseCase,
            ChangeDocumentTypeStatusUseCase changeDocumentTypeStatusUseCase,
            DeleteDocumentTypeUseCase deleteDocumentTypeUseCase) {

        this.createDocumentTypeUseCase =
                createDocumentTypeUseCase;

        this.getAllDocumentTypesUseCase =
                getAllDocumentTypesUseCase;

        this.getDocumentTypeFormUseCase =
                getDocumentTypeFormUseCase;

        this.validateDocumentTemplateVariablesUseCase =
                validateDocumentTemplateVariablesUseCase;

        this.generateDocumentUseCase =
                generateDocumentUseCase;

        this.updateDocumentTypeUseCase =
                updateDocumentTypeUseCase;

        this.changeDocumentTypeStatusUseCase =
                changeDocumentTypeStatusUseCase;

        this.deleteDocumentTypeUseCase =
                deleteDocumentTypeUseCase;
    }

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(
            @RequestBody CreateDocumentTypeRequest request) {

        DocumentTypeResponse response =
                createDocumentTypeUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<DocumentTypeResponse>>
            getAll() {

        return ResponseEntity.ok(
                getAllDocumentTypesUseCase.execute());
    }

    @PutMapping("/{documentTypeId}")
    public ResponseEntity<DocumentTypeResponse> update(
            @PathVariable Long documentTypeId,
            @RequestBody CreateDocumentTypeRequest request) {

        return ResponseEntity.ok(
                updateDocumentTypeUseCase.update(
                        documentTypeId,
                        request));
    }

    @PatchMapping("/{documentTypeId}/statut")
    public ResponseEntity<DocumentTypeResponse> changeStatus(
            @PathVariable Long documentTypeId,
            @RequestBody DocumentTypeStatusRequest request) {

        return ResponseEntity.ok(
                changeDocumentTypeStatusUseCase.changeStatus(
                        documentTypeId,
                        request.getActif()));
    }

    @DeleteMapping("/{documentTypeId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long documentTypeId) {

        deleteDocumentTypeUseCase.delete(documentTypeId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{documentTypeId}/form")
    public ResponseEntity<DocumentTypeFormResponse>
            getForm(
                    @PathVariable Long documentTypeId) {

        return ResponseEntity.ok(
                getDocumentTypeFormUseCase
                        .getForm(documentTypeId));
    }

    @GetMapping("/{documentTypeId}/template/validate-variables")
    public ResponseEntity<DocumentTemplateValidationResponse>
            validateTemplateVariables(
                    @PathVariable Long documentTypeId) {

        return ResponseEntity.ok(
                validateDocumentTemplateVariablesUseCase
                        .execute(documentTypeId));
    }

    @PostMapping("/{documentTypeId}/generate")
    public ResponseEntity<byte[]> generate(
            @PathVariable Long documentTypeId,
            @RequestParam(required = false) Long missionId,
            @RequestBody(required = false) Map<String, Object> values) {

        GeneratedDocument document =
                generateDocumentUseCase.generate(
                        documentTypeId,
                        values,
                        missionId);

        return FileResponses.attachment(document);
    }
}
