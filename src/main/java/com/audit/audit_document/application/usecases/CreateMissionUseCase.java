package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.CreateMissionRequest;
import com.audit.audit_document.domain.entity.Mission;

public interface CreateMissionUseCase {

    Mission execute(CreateMissionRequest request);
}