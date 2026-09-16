package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.MissionResponse;

public interface GetByIdMissionUseCase {

    MissionResponse execute(Long missionId);
}