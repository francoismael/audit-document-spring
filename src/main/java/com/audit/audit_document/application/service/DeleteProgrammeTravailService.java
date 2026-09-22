package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteProgrammeTravailUseCase;
import com.audit.audit_document.domain.repository.ProgrammeTravailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteProgrammeTravailService
        implements DeleteProgrammeTravailUseCase {

    private final ProgrammeTravailRepository programmeRepository;

    public DeleteProgrammeTravailService(
            ProgrammeTravailRepository programmeRepository) {

        this.programmeRepository = programmeRepository;
    }

    @Override
    public void delete(Long id) {

        if (!programmeRepository.findById(id).isPresent()) {

            throw new RuntimeException(
                    "Programme de travail introuvable"
            );
        }

        programmeRepository.deleteById(id);
    }
}