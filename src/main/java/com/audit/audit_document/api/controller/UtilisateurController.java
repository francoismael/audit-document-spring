package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.ChangeUtilisateurStatusRequest;
import com.audit.audit_document.application.dto.CreateUtilisateurRequest;
import com.audit.audit_document.application.dto.ResetPasswordRequest;
import com.audit.audit_document.application.dto.UpdateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.usecases.ChangeUtilisateurStatusUseCase;
import com.audit.audit_document.application.usecases.CreateUtilisateurUseCase;
import com.audit.audit_document.application.usecases.DeleteUtilisateurUseCase;
import com.audit.audit_document.application.usecases.GetAllUtilisateursUseCase;
import com.audit.audit_document.application.usecases.GetUtilisateurByIdUseCase;
import com.audit.audit_document.application.usecases.ResetUtilisateurPasswordUseCase;
import com.audit.audit_document.application.usecases.UpdateUtilisateurUseCase;
import com.audit.audit_document.domain.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Account administration: every route is reserved to administrators (SecurityConfig).
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final CreateUtilisateurUseCase createUtilisateurUseCase;
    private final GetAllUtilisateursUseCase getAllUtilisateursUseCase;
    private final GetUtilisateurByIdUseCase getUtilisateurByIdUseCase;
    private final UpdateUtilisateurUseCase updateUtilisateurUseCase;
    private final DeleteUtilisateurUseCase deleteUtilisateurUseCase;
    private final ChangeUtilisateurStatusUseCase changeUtilisateurStatusUseCase;
    private final ResetUtilisateurPasswordUseCase resetUtilisateurPasswordUseCase;

    public UtilisateurController(
            CreateUtilisateurUseCase createUtilisateurUseCase,
            GetAllUtilisateursUseCase getAllUtilisateursUseCase,
            GetUtilisateurByIdUseCase getUtilisateurByIdUseCase,
            UpdateUtilisateurUseCase updateUtilisateurUseCase,
            DeleteUtilisateurUseCase deleteUtilisateurUseCase,
            ChangeUtilisateurStatusUseCase changeUtilisateurStatusUseCase,
            ResetUtilisateurPasswordUseCase resetUtilisateurPasswordUseCase) {

        this.createUtilisateurUseCase = createUtilisateurUseCase;
        this.getAllUtilisateursUseCase = getAllUtilisateursUseCase;
        this.getUtilisateurByIdUseCase = getUtilisateurByIdUseCase;
        this.updateUtilisateurUseCase = updateUtilisateurUseCase;
        this.deleteUtilisateurUseCase = deleteUtilisateurUseCase;
        this.changeUtilisateurStatusUseCase = changeUtilisateurStatusUseCase;
        this.resetUtilisateurPasswordUseCase = resetUtilisateurPasswordUseCase;
    }

    @PostMapping
    public ResponseEntity<UtilisateurResponse> create(
            @RequestBody CreateUtilisateurRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createUtilisateurUseCase.create(request));
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> getAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean actif) {

        return ResponseEntity.ok(getAllUtilisateursUseCase.getAll(q, role, actif));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> getById(@PathVariable Long id) {

        return ResponseEntity.ok(getUtilisateurByIdUseCase.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateUtilisateurRequest request) {

        return ResponseEntity.ok(updateUtilisateurUseCase.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        deleteUtilisateurUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/actif")
    public ResponseEntity<UtilisateurResponse> changeStatus(
            @PathVariable Long id,
            @RequestBody ChangeUtilisateurStatusRequest request) {

        return ResponseEntity.ok(changeUtilisateurStatusUseCase.changeStatus(id, request.getActif()));
    }

    @PatchMapping("/{id}/mot-de-passe")
    public ResponseEntity<Void> resetPassword(
            @PathVariable Long id,
            @RequestBody ResetPasswordRequest request) {

        resetUtilisateurPasswordUseCase.resetPassword(id, request);

        return ResponseEntity.noContent().build();
    }
}
