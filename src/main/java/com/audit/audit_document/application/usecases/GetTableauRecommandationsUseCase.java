package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.TableauRecommandationsResponse;

public interface GetTableauRecommandationsUseCase {

    TableauRecommandationsResponse getByMissionId(
            Long missionId
    );
}