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

import com.audit.audit_document.application.dto.CreateReponseRequest;
import com.audit.audit_document.application.dto.ReponseResponse;
import com.audit.audit_document.application.usecases.CreateReponseUseCase;
import com.audit.audit_document.application.usecases.DeleteReponseUseCase;
import com.audit.audit_document.application.usecases.GetAllReponsesUseCase;
import com.audit.audit_document.application.usecases.GetReponseByIdUseCase;
import com.audit.audit_document.application.usecases.GetReponsesByRecommandationUseCase;
import com.audit.audit_document.application.usecases.UpdateReponseUseCase;

@RestController
@RequestMapping("/api/reponses")
public class ReponseController {

    private final CreateReponseUseCase createReponseUseCase;
    private final GetReponseByIdUseCase getReponseByIdUseCase;
    private final GetAllReponsesUseCase getAllReponsesUseCase;
    private final GetReponsesByRecommandationUseCase
            getReponsesByRecommandationUseCase;
    private final UpdateReponseUseCase updateReponseUseCase;
    private final DeleteReponseUseCase deleteReponseUseCase;

    public ReponseController(
            CreateReponseUseCase createReponseUseCase,
            GetReponseByIdUseCase getReponseByIdUseCase,
            GetAllReponsesUseCase getAllReponsesUseCase,
            GetReponsesByRecommandationUseCase
                    getReponsesByRecommandationUseCase,
            UpdateReponseUseCase updateReponseUseCase,
            DeleteReponseUseCase deleteReponseUseCase) {

        this.createReponseUseCase =
                createReponseUseCase;

        this.getReponseByIdUseCase =
                getReponseByIdUseCase;

        this.getAllReponsesUseCase =
                getAllReponsesUseCase;

        this.getReponsesByRecommandationUseCase =
                getReponsesByRecommandationUseCase;

        this.updateReponseUseCase =
                updateReponseUseCase;

        this.deleteReponseUseCase =
                deleteReponseUseCase;
    }


    @PostMapping
    public ResponseEntity<ReponseResponse> create(
            @RequestBody CreateReponseRequest request) {

        ReponseResponse response =
                createReponseUseCase.create(
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<ReponseResponse>> getAll() {

        return ResponseEntity.ok(
                getAllReponsesUseCase.getAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReponseResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                getReponseByIdUseCase.getById(id)
        );
    }


    @GetMapping(
            "/recommandation/{recommandationId}"
    )
    public ResponseEntity<List<ReponseResponse>>
            getByRecommandation(
                    @PathVariable Long recommandationId) {

        return ResponseEntity.ok(
                getReponsesByRecommandationUseCase
                        .getByRecommandationId(
                                recommandationId
                        )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReponseResponse> update(
            @PathVariable Long id,
            @RequestBody CreateReponseRequest request) {

        return ResponseEntity.ok(
                updateReponseUseCase.update(
                        id,
                        request
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteReponseUseCase.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}