package com.audit.audit_document.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.audit_document.application.usecases.DeleteDeclarationIndependanceUseCase;
import com.audit.audit_document.domain.repository.DeclarationIndependanceRepository;

@Service
public class DeleteDeclarationIndependanceService
        implements DeleteDeclarationIndependanceUseCase {

    private final DeclarationIndependanceRepository declarationRepository;

    public DeleteDeclarationIndependanceService(
            DeclarationIndependanceRepository declarationRepository) {

        this.declarationRepository = declarationRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        declarationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Déclaration d'indépendance introuvable : " + id));

        declarationRepository.deleteById(id);
    }
}