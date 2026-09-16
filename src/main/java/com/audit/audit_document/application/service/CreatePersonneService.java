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
        personne.setNom(request.getNom());
        personne.setPrenom(request.getPrenom());
        personne.setMatricule(request.getMatricule());
        personne.setFonction(request.getFonction());
        personne.setServices(request.getServices());

        return personneRepository.save(personne);
    }
}
