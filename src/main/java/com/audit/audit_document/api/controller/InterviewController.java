package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateInterviewRequest;
import com.audit.audit_document.application.dto.InterviewResponse;
import com.audit.audit_document.application.usecases.CreateInterviewUseCase;
import com.audit.audit_document.application.usecases.DeleteInterviewUseCase;
import com.audit.audit_document.application.usecases.GetAllInterviewsUseCase;
import com.audit.audit_document.application.usecases.GetInterviewByIdUseCase;
import com.audit.audit_document.application.usecases.UpdateInterviewUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final CreateInterviewUseCase createInterviewUseCase;
    private final GetAllInterviewsUseCase getAllInterviewsUseCase;
    private final GetInterviewByIdUseCase getInterviewByIdUseCase;
    private final UpdateInterviewUseCase updateInterviewUseCase;
    private final DeleteInterviewUseCase deleteInterviewUseCase;

    public InterviewController(
            CreateInterviewUseCase createInterviewUseCase,
            GetAllInterviewsUseCase getAllInterviewsUseCase,
            GetInterviewByIdUseCase getInterviewByIdUseCase,
            UpdateInterviewUseCase updateInterviewUseCase,
            DeleteInterviewUseCase deleteInterviewUseCase) {

        this.createInterviewUseCase = createInterviewUseCase;
        this.getAllInterviewsUseCase = getAllInterviewsUseCase;
        this.getInterviewByIdUseCase = getInterviewByIdUseCase;
        this.updateInterviewUseCase = updateInterviewUseCase;
        this.deleteInterviewUseCase = deleteInterviewUseCase;
    }

    @PostMapping
    public ResponseEntity<InterviewResponse> create(
        @RequestBody CreateInterviewRequest request) {

    return ResponseEntity.ok(
            createInterviewUseCase.execute(request)
    );
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getAll() {

        return ResponseEntity.ok(
                getAllInterviewsUseCase.execute()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                getInterviewByIdUseCase.execute(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponse> update(
        @PathVariable Long id,
        @RequestBody CreateInterviewRequest request) {

    return ResponseEntity.ok(
            updateInterviewUseCase.execute(id, request)
    );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteInterviewUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}