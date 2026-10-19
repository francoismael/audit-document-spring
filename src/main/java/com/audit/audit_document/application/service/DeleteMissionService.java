package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.application.usecases.DeleteMissionUseCase;
import com.audit.audit_document.domain.repository.DocumentGenereRepository;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMissionService implements DeleteMissionUseCase {

    private final MissionRepository missionRepository;

    private final DocumentGenereRepository documentGenereRepository;

    public DeleteMissionService(
            MissionRepository missionRepository,
            DocumentGenereRepository documentGenereRepository) {

        this.missionRepository = missionRepository;
        this.documentGenereRepository = documentGenereRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        missionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Mission introuvable : " + id
                        ));

        // Generated documents stay in the history without their mission.
        documentGenereRepository.detachMission(id);

        missionRepository.deleteById(id);
    }
}
