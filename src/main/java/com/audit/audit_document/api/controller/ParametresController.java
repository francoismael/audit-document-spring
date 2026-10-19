package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.ParametresResponse;
import com.audit.audit_document.application.usecases.GetParametresUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parametres")
public class ParametresController {

    private final GetParametresUseCase getParametresUseCase;

    public ParametresController(GetParametresUseCase getParametresUseCase) {

        this.getParametresUseCase = getParametresUseCase;
    }

    @GetMapping
    public ResponseEntity<ParametresResponse> getSettings() {

        return ResponseEntity.ok(getParametresUseCase.getSettings());
    }
}
