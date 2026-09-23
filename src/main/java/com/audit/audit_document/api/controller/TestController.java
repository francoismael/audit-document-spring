package com.audit.audit_document.api.controller;

import com.audit.audit_document.application.dto.CreateTestRequest;
import com.audit.audit_document.application.dto.TestResponse;
import com.audit.audit_document.application.usecases.CreateTestUseCase;
import com.audit.audit_document.application.usecases.DeleteTestUseCase;
import com.audit.audit_document.application.usecases.GetAllTestsUseCase;
import com.audit.audit_document.application.usecases.GetTestByIdUseCase;
import com.audit.audit_document.application.usecases.GetTestsByLigneProgrammeUseCase;
import com.audit.audit_document.application.usecases.UpdateTestUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final CreateTestUseCase createTestUseCase;
    private final GetTestByIdUseCase getTestByIdUseCase;
    private final GetAllTestsUseCase getAllTestsUseCase;
    private final GetTestsByLigneProgrammeUseCase getTestsByLigneProgrammeUseCase;
    private final UpdateTestUseCase updateTestUseCase;
    private final DeleteTestUseCase deleteTestUseCase;

    public TestController(
            CreateTestUseCase createTestUseCase,
            GetTestByIdUseCase getTestByIdUseCase,
            GetAllTestsUseCase getAllTestsUseCase,
            GetTestsByLigneProgrammeUseCase getTestsByLigneProgrammeUseCase,
            UpdateTestUseCase updateTestUseCase,
            DeleteTestUseCase deleteTestUseCase) {

        this.createTestUseCase = createTestUseCase;
        this.getTestByIdUseCase = getTestByIdUseCase;
        this.getAllTestsUseCase = getAllTestsUseCase;
        this.getTestsByLigneProgrammeUseCase =
                getTestsByLigneProgrammeUseCase;
        this.updateTestUseCase = updateTestUseCase;
        this.deleteTestUseCase = deleteTestUseCase;
    }

    @PostMapping
    public ResponseEntity<TestResponse> create(
            @RequestBody CreateTestRequest request) {

        TestResponse response =
                createTestUseCase.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<TestResponse>> getAll() {

        List<TestResponse> responses =
                getAllTestsUseCase.getAll();

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/ligne-programme/{ligneProgrammeId}")
    public ResponseEntity<List<TestResponse>> getByLigneProgramme(
            @PathVariable Long ligneProgrammeId) {

        List<TestResponse> responses =
                getTestsByLigneProgrammeUseCase
                        .getByLigneProgrammeId(
                                ligneProgrammeId
                        );

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getById(
            @PathVariable Long id) {

        TestResponse response =
                getTestByIdUseCase.getById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResponse> update(
            @PathVariable Long id,
            @RequestBody CreateTestRequest request) {

        TestResponse response =
                updateTestUseCase.update(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        deleteTestUseCase.delete(id);

        return ResponseEntity.noContent().build();
    }
}