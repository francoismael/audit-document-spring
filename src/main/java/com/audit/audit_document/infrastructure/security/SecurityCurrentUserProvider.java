package com.audit.audit_document.infrastructure.security;

import com.audit.audit_document.application.port.CurrentUserProvider;
import com.audit.audit_document.domain.entity.Utilisateur;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * Reads the account placed in the security context by JwtAuthenticationFilter.
 */
@Component
public class SecurityCurrentUserProvider implements CurrentUserProvider {

    @Override
    public Utilisateur getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof Utilisateur)) {

            throw new AuthenticationCredentialsNotFoundException(
                    "Authentification requise");
        }

        return (Utilisateur) authentication.getPrincipal();
    }
}
