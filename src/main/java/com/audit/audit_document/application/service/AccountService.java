package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.ChangePasswordRequest;
import com.audit.audit_document.application.dto.RegisterRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.port.CurrentUserProvider;
import com.audit.audit_document.application.usecases.ChangePasswordUseCase;
import com.audit.audit_document.application.usecases.GetCurrentAccountUseCase;
import com.audit.audit_document.application.usecases.RegisterUseCase;
import com.audit.audit_document.domain.entity.Role;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/*
 * What an account does for itself: sign up, read its profile, change its password.
 */
@Service
public class AccountService
        implements RegisterUseCase,
                   GetCurrentAccountUseCase,
                   ChangePasswordUseCase {

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurRules utilisateurRules;

    private final CurrentUserProvider currentUserProvider;

    public AccountService(
            UtilisateurRepository utilisateurRepository,
            UtilisateurRules utilisateurRules,
            CurrentUserProvider currentUserProvider) {

        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurRules = utilisateurRules;
        this.currentUserProvider = currentUserProvider;
    }

    // A new account is always a user, inactive until an administrator activates it and links it to a person.
    @Override
    @Transactional
    public UtilisateurResponse register(RegisterRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Les informations du compte sont obligatoires.");
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateurRules.applyUsername(utilisateur, request.getUsername());
        utilisateurRules.applyPassword(utilisateur, request.getPassword());

        utilisateur.setRole(Role.UTILISATEUR);
        utilisateur.setActif(false);
        utilisateur.setDateCreation(LocalDateTime.now());

        return UtilisateurResponse.from(utilisateurRepository.save(utilisateur));
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponse getCurrentAccount() {

        return UtilisateurResponse.from(findCurrentUser());
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Les mots de passe sont obligatoires.");
        }

        Utilisateur utilisateur = findCurrentUser();

        if (!utilisateurRules.matchesPassword(utilisateur, request.getMotDePasseActuel())) {
            throw new IllegalArgumentException("Le mot de passe actuel est incorrect.");
        }

        utilisateurRules.applyPassword(utilisateur, request.getNouveauMotDePasse());

        utilisateurRepository.save(utilisateur);
    }

    // Read again from the database: the account in the security context comes from the start of the request.
    private Utilisateur findCurrentUser() {

        Long id = currentUserProvider.getCurrentUser().getId();

        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
    }
}
