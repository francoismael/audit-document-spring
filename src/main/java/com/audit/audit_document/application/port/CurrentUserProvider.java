package com.audit.audit_document.application.port;

import com.audit.audit_document.domain.entity.Utilisateur;

/*
 * Gives the account that sends the current request.
 */
public interface CurrentUserProvider {

    // Throws an AuthenticationException when no account is connected.
    Utilisateur getCurrentUser();
}
