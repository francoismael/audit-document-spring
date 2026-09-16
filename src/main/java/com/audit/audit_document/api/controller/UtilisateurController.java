package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.ChangeUtilisateurStatusRequest;
import com.audit.audit_document.application.dto.CreateUtilisateurRequest;
import com.audit.audit_document.application.dto.UpdateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.ChangeUtilisateurStatusUseCase;
import com.audit.audit_document.application.usecases.CreateUtilisateurUseCase;
import com.audit.audit_document.application.usecases.DeleteUtilisateurUseCase;
import com.audit.audit_document.application.usecases.GetAllUtilisateursUseCase;
import com.audit.audit_document.application.usecases.GetUtilisateurByIdUseCase;
import com.audit.audit_document.application.usecases.UpdateUtilisateurUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final CreateUtilisateurUseCase createUtilisateurUseCase;
    private final GetAllUtilisateursUseCase getAllUtilisateursUseCase;
    private final GetUtilisateurByIdUseCase getUtilisateurByIdUseCase;
    private final UpdateUtilisateurUseCase updateUtilisateurUseCase;
    private final DeleteUtilisateurUseCase deleteUtilisateurUseCase;
    private final ChangeUtilisateurStatusUseCase changeUtilisateurStatusUseCase;

public UtilisateurController(
        CreateUtilisateurUseCase createUtilisateurUseCase,
        GetAllUtilisateursUseCase getAllUtilisateursUseCase,
        GetUtilisateurByIdUseCase getUtilisateurByIdUseCase,
        UpdateUtilisateurUseCase updateUtilisateurUseCase,
        DeleteUtilisateurUseCase deleteUtilisateurUseCase,
        ChangeUtilisateurStatusUseCase changeUtilisateurStatusUseCase
) {
    this.createUtilisateurUseCase = createUtilisateurUseCase;
    this.getAllUtilisateursUseCase = getAllUtilisateursUseCase;
    this.getUtilisateurByIdUseCase = getUtilisateurByIdUseCase;
    this.updateUtilisateurUseCase = updateUtilisateurUseCase;
    this.deleteUtilisateurUseCase = deleteUtilisateurUseCase;
    this.changeUtilisateurStatusUseCase = changeUtilisateurStatusUseCase;
}
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UtilisateurResponse> create(
            @RequestBody CreateUtilisateurRequest request
    ) {
        UtilisateurResponse response =
                createUtilisateurUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> getAll() {
    return ResponseEntity.ok(
            getAllUtilisateursUseCase.execute()
    );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> getById(
        @PathVariable Long id
    ) {
    return ResponseEntity.ok(
            getUtilisateurByIdUseCase.execute(id)
    );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> update(
        @PathVariable Long id,
        @RequestBody UpdateUtilisateurRequest request
    ) {
    return ResponseEntity.ok(
            updateUtilisateurUseCase.execute(id, request)
    );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

    deleteUtilisateurUseCase.execute(id);

    return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/actif")
    public ResponseEntity<UtilisateurResponse> changeStatus(
        @PathVariable Long id,
        @RequestBody ChangeUtilisateurStatusRequest request
    ) {
    return ResponseEntity.ok(
            changeUtilisateurStatusUseCase.execute(id, request.getActif())
    );
    }
}