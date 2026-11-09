package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UpdatePersonneRequest;
import com.audit.audit_document.application.usecases.UpdatePersonneUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePersonneService implements UpdatePersonneUseCase {

    private final PersonneRepository personneRepository;

    private final PersonneRules personneRules;

    public UpdatePersonneService(
            PersonneRepository personneRepository,
            PersonneRules personneRules) {

        this.personneRepository = personneRepository;
        this.personneRules = personneRules;
    }

    // The matricule never changes once given.
    @Override
    @Transactional
    public Personne execute(Long id, UpdatePersonneRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Les informations de la personne sont obligatoires.");
        }

        Personne personne = personneRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Personne introuvable avec l'id : " + id
                        )
                );

        personneRules.applyFields(
                personne,
                request.getNom(),
                request.getPrenom(),
                request.getFonction(),
                request.getServices(),
                request.getSexe());

        return personneRepository.save(personne);
    }
}
