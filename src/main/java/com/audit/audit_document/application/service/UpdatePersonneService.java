package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.UpdatePersonneRequest;
import com.audit.audit_document.application.usecases.UpdatePersonneUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;

import org.springframework.stereotype.Service;

@Service
public class UpdatePersonneService implements UpdatePersonneUseCase {

    private final PersonneRepository personneRepository;

    public UpdatePersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public Personne execute(Long id, UpdatePersonneRequest request) {

        Personne personne = personneRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Personne introuvable avec l'id : " + id
                        )
                );

        personne.setNom(request.getNom());
        personne.setPrenom(request.getPrenom());
        personne.setMatricule(request.getMatricule());
        personne.setFonction(request.getFonction());
        personne.setServices(request.getServices());

        return personneRepository.save(personne);
    }
}