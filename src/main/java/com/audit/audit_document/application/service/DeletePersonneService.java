package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeletePersonneUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;

import org.springframework.stereotype.Service;

@Service
public class DeletePersonneService implements DeletePersonneUseCase {

    private final PersonneRepository personneRepository;

    public DeletePersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public void execute(Long id) {

        Personne personne = personneRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Personne introuvable avec l'id : " + id
                        )
                );

        personneRepository.deleteById(personne.getId());
    }
}