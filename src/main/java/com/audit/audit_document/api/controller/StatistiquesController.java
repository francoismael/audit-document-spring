package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.StatistiquesResponse;
import com.audit.audit_document.application.usecases.GetStatistiquesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiquesController {

    private final GetStatistiquesUseCase getStatistiquesUseCase;

    public StatistiquesController(
            GetStatistiquesUseCase getStatistiquesUseCase) {

        this.getStatistiquesUseCase = getStatistiquesUseCase;
    }

    @GetMapping
    public ResponseEntity<StatistiquesResponse> getStatistics() {

        return ResponseEntity.ok(
                getStatistiquesUseCase.getStatistics());
    }
}
