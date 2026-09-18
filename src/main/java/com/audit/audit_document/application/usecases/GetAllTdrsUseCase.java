package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.TdrResponse;

import java.util.List;

public interface GetAllTdrsUseCase {

    List<TdrResponse> execute();
}