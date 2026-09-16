package com.audit.audit_document.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audit.audit_document.application.dto.CreatePersonneRequest;
import com.audit.audit_document.application.dto.UpdatePersonneRequest;
import com.audit.audit_document.application.usecases.CreatePersonneUseCase;
import com.audit.audit_document.application.usecases.DeletePersonneUseCase;
import com.audit.audit_document.application.usecases.GetAllPersonnesUseCase;
import com.audit.audit_document.application.usecases.GetPersonneByIdUseCase;
import com.audit.audit_document.application.usecases.UpdatePersonneUseCase;
import com.audit.audit_document.domain.entity.Personne;

@RestController 
@RequestMapping("api/personnes")
public class PersonneController {
    private final CreatePersonneUseCase createPersonneUseCase;
    private final GetAllPersonnesUseCase getAllPersonnesUseCase;
    private final GetPersonneByIdUseCase getPersonneByIdUseCase;
    private final UpdatePersonneUseCase updatePersonneUseCase;
    private final DeletePersonneUseCase deletePersonneUseCase;

    public PersonneController(CreatePersonneUseCase createPersonneUseCase,
        GetAllPersonnesUseCase getAllPersonnesUseCase,
        GetPersonneByIdUseCase getPersonneByIdUseCase,
        UpdatePersonneUseCase updatePersonneUseCase,
        DeletePersonneUseCase deletePersonneUseCase
        ){
        this.createPersonneUseCase = createPersonneUseCase;
        this.getAllPersonnesUseCase = getAllPersonnesUseCase;
        this.getPersonneByIdUseCase = getPersonneByIdUseCase;
        this.updatePersonneUseCase = updatePersonneUseCase;
        this.deletePersonneUseCase = deletePersonneUseCase;
    }

    @PostMapping 
    public ResponseEntity<Personne> create(@RequestBody CreatePersonneRequest request){
        Personne personne = createPersonneUseCase.execute(request);
        return ResponseEntity.ok(personne);
    }

    @GetMapping
    public ResponseEntity<List<Personne>> getAll() {

    List<Personne> personnes =
            getAllPersonnesUseCase.execute();

    return ResponseEntity.ok(personnes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getById(
        @PathVariable Long id
    ) {
    Personne personne = getPersonneByIdUseCase.execute(id);

    return ResponseEntity.ok(personne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personne> update(
        @PathVariable Long id,
        @RequestBody UpdatePersonneRequest request
    ) {
    Personne personne =
            updatePersonneUseCase.execute(id, request);

    return ResponseEntity.ok(personne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id
    ) {
    deletePersonneUseCase.execute(id);

    return ResponseEntity.noContent().build();
    }
}
