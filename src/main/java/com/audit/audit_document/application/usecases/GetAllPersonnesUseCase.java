package com.audit.audit_document.application.usecases;

import com.audit.audit_document.domain.entity.Personne;

import java.util.List;

public interface GetAllPersonnesUseCase {

    List<Personne> execute();
}