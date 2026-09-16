 package com.audit.audit_document.application.service;

import com.audit.audit_document.application.usecases.DeleteMissionUseCase;
import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMissionService implements DeleteMissionUseCase {

    private final MissionRepository missionRepository;

    public DeleteMissionService(
            MissionRepository missionRepository) {

        this.missionRepository = missionRepository;
    }

    @Override
    @Transactional
    public void execute(Long id) {

        Mission mission = missionRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Mission introuvable : " + id
                        ));

        missionRepository.deleteById(id);
    }
}