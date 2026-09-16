package com.audit.audit_document.application.usecases;

import java.util.List;

import com.audit.audit_document.application.dto.MissionResponse;

public interface GetAllMissionUseCase {

    List<MissionResponse> execute();
}