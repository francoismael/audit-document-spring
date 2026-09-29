package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.PvAffirmationResponse;

public interface GetPvAffirmationByMissionUseCase {

    PvAffirmationResponse getByMissionId(
            Long missionId
    );
}