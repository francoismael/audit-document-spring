package com.audit.audit_document.application.usecases;

import java.util.List;

import com.audit.audit_document.application.dto.MissionResponse;

public interface GetAllMissionUseCase {

    // query: numero, intitule or structure (optional); statut: A_VENIR, EN_COURS, TERMINEE or SANS_DATES (optional).
    List<MissionResponse> execute(String query, String statut);
}
