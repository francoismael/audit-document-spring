package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.MissionPersonneResponse;

import java.util.List;

public interface GetMissionPersonnesByMissionUseCase {

    List<MissionPersonneResponse> getByMissionId(
            Long missionId
    );
}