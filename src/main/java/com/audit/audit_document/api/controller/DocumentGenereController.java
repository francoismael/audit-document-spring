package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.DocumentGenereResponse;
import com.audit.audit_document.application.dto.GeneratedDocument;
import com.audit.audit_document.application.usecases.DeleteDocumentGenereUseCase;
import com.audit.audit_document.application.usecases.DownloadDocumentGenereUseCase;
import com.audit.audit_document.application.usecases.GetDocumentGenereByIdUseCase;
import com.audit.audit_document.application.usecases.GetDocumentsGeneresUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents-generes")
public class DocumentGenereController {

    private final GetDocumentsGeneresUseCase getAllUseCase;
    private final GetDocumentGenereByIdUseCase getByIdUseCase;
    private final DownloadDocumentGenereUseCase downloadUseCase;
    private final DeleteDocumentGenereUseCase deleteUseCase;

    public DocumentGenereController(
            GetDocumentsGeneresUseCase getAllUseCase,
            GetDocumentGenereByIdUseCase getByIdUseCase,
            DownloadDocumentGenereUseCase downloadUseCase,
            DeleteDocumentGenereUseCase deleteUseCase) {

        this.getAllUseCase = getAllUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.downloadUseCase = downloadUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @GetMapping
    public ResponseEntity<List<DocumentGenereResponse>> getAll(
            @RequestParam(required = false) Long documentTypeId,
            @RequestParam(required = false) Long missionId) {

        return ResponseEntity.ok(
                getAllUseCase.getAll(documentTypeId, missionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentGenereResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(getByIdUseCase.getById(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(
            @PathVariable Long id) {

        GeneratedDocument document = downloadUseCase.download(id);

        return FileResponses.attachment(document);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}
