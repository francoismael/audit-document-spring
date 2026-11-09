package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.OrdreSectionsRequest;
import com.audit.audit_document.application.dto.OrdreSectionsResponse;

public interface SaveOrdreSectionsUseCase {

    // Replaces the whole order of the document.
    OrdreSectionsResponse save(String documentCode, OrdreSectionsRequest request);
}
