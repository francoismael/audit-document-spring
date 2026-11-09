package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreatePersonneRequest;
import com.audit.audit_document.application.usecases.CreatePersonneUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePersonneService implements CreatePersonneUseCase {

    private final PersonneRepository personneRepository;

    private final PersonneRules personneRules;

    public CreatePersonneService(
            PersonneRepository personneRepository,
            PersonneRules personneRules) {

        this.personneRepository = personneRepository;
        this.personneRules = personneRules;
    }

    // The matricule is given by the backend (MAT008, MAT009...), never by the form.
    @Override
    @Transactional
    public Personne execute(CreatePersonneRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Les informations de la personne sont obligatoires.");
        }

        Personne personne = new Personne();

        personneRules.applyFields(
                personne,
                request.getNom(),
                request.getPrenom(),
                request.getFonction(),
                request.getServices(),
                request.getSexe());

        personne.setMatricule(personneRules.nextMatricule());

        return personneRepository.save(personne);
    }
}
