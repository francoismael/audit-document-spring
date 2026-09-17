package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteReunionUseCase;
import com.audit.audit_document.domain.repository.ReunionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteReunionService
        implements DeleteReunionUseCase {

    private final ReunionRepository reunionRepository;

    public DeleteReunionService(
            ReunionRepository reunionRepository) {

        this.reunionRepository = reunionRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        if (!reunionRepository.findById(id).isPresent()) {

            throw new RuntimeException(
                    "Réunion introuvable");
        }

        reunionRepository.deleteById(id);
    }
}