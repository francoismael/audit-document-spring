package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.DocumentTemplateResponse;
import com.audit.audit_document.application.usecases.UploadDocumentTemplateUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document-types")
public class DocumentTemplateController {

    private final UploadDocumentTemplateUseCase
            uploadDocumentTemplateUseCase;

    public DocumentTemplateController(
            UploadDocumentTemplateUseCase
                    uploadDocumentTemplateUseCase) {

        this.uploadDocumentTemplateUseCase =
                uploadDocumentTemplateUseCase;
    }

    @PostMapping(
            value = "/{documentTypeId}/template",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<DocumentTemplateResponse>
            uploadTemplate(
                    @PathVariable Long documentTypeId,
                    @RequestParam("file")
                    MultipartFile file) {

        return ResponseEntity.ok(
                uploadDocumentTemplateUseCase.execute(
                        documentTypeId,
                        file
                )
        );
    }
}