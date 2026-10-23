package com.audit.audit_document.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.audit.audit_document.application.dto.CreatePersonneRequest;
import com.audit.audit_document.application.usecases.CreatePersonneUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;

@Service 
public class CreatePersonneService implements CreatePersonneUseCase {
    private final PersonneRepository personneRepository;

    public CreatePersonneService(PersonneRepository personneRepository){
        this.personneRepository = personneRepository;
    }

    @Override 
    @Transactional 

    public Personne execute(CreatePersonneRequest request){
        Personne personne = new Personne();
        personne.setNom(requireText(request.getNom(), "Le nom de la personne est obligatoire."));
        personne.setPrenom(requireText(request.getPrenom(), "Le prénom de la personne est obligatoire."));
        personne.setMatricule(trimToNull(request.getMatricule()));
        personne.setFonction(trimToNull(request.getFonction()));
        personne.setServices(trimToNull(request.getServices()));

        return personneRepository.save(personne);
    }

    private static String requireText(String value, String message) {
        String text = trimToNull(value);

        if (text == null) {
            throw new IllegalArgumentException(message);
        }

        return text;
    }

    private static String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}
