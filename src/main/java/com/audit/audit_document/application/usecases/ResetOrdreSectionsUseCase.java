package com.audit.audit_document.application.usecases;

public interface ResetOrdreSectionsUseCase {

    // Back to the default order of the official model.
    void reset(String documentCode);
}
