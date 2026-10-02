package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.AvancementMissionResponse;

import java.util.List;

public interface GetAvancementMissionsUseCase {

    List<AvancementMissionResponse> execute();
}