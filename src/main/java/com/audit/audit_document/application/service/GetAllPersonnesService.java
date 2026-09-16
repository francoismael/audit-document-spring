package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.GetAllPersonnesUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.repository.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPersonnesService implements GetAllPersonnesUseCase {

    private final PersonneRepository personneRepository;

    public GetAllPersonnesService(
            PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public List<Personne> execute() {

        return personneRepository.findAll();
    }
}