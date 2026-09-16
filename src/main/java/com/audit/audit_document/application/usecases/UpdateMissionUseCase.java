package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.application.dto.MissionResponse;

public interface UpdateMissionUseCase {

    MissionResponse execute(
            Long id,
            CreateMissionRequest request
    );
}