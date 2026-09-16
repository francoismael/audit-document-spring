package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.GetPersonneByIdUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;

import org.springframework.stereotype.Service;

@Service
public class GetPersonneByIdService implements GetPersonneByIdUseCase {

    private final PersonneRepository personneRepository;

    public GetPersonneByIdService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public Personne execute(Long id) {

        return personneRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Personne introuvable avec l'id : " + id)
                );
    }
}