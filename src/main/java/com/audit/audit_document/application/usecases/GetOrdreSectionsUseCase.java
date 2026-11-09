package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.OrdreSectionsResponse;

import java.util.List;

public interface GetOrdreSectionsUseCase {

    // Only the business documents whose order was changed; the others keep their default order.
    List<OrdreSectionsResponse> getAll();
}
