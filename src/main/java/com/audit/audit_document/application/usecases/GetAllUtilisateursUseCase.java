package com.audit.audit_document.application.usecases;

import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.domain.entity.Role;

import java.util.List;

public interface GetAllUtilisateursUseCase {

    // Every filter is optional: the search looks at the username and the linked person.
    List<UtilisateurResponse> getAll(String query, Role role, Boolean actif);
}
