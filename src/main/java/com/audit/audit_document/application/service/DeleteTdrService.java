package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteTdrUseCase;
import com.audit.audit_document.domain.repository.TdrRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteTdrService
        implements DeleteTdrUseCase {

    private final TdrRepository tdrRepository;

    public DeleteTdrService(
            TdrRepository tdrRepository) {

        this.tdrRepository = tdrRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        if (!tdrRepository.findById(id).isPresent()) {

            throw new RuntimeException(
                    "TDR introuvable : " + id);
        }

        tdrRepository.deleteById(id);
    }
}