package com.audit.audit_document.application.service;

import com.audit.audit_document.application.dto.CreateUtilisateurRequest;
import com.audit.audit_document.application.dto.ResetPasswordRequest;
import com.audit.audit_document.application.dto.UpdateUtilisateurRequest;
import com.audit.audit_document.application.dto.UtilisateurResponse;
import com.audit.audit_document.application.port.CurrentUserProvider;
import com.audit.audit_document.application.usecases.ChangeUtilisateurStatusUseCase;
import com.audit.audit_document.application.usecases.CreateUtilisateurUseCase;
import com.audit.audit_document.application.usecases.DeleteUtilisateurUseCase;
import com.audit.audit_document.application.usecases.GetAllUtilisateursUseCase;
import com.audit.audit_document.application.usecases.GetUtilisateurByIdUseCase;
import com.audit.audit_document.application.usecases.ResetUtilisateurPasswordUseCase;
import com.audit.audit_document.application.usecases.UpdateUtilisateurUseCase;
import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Role;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Account administration (routes reserved to administrators in SecurityConfig).
 */
@Service
public class UtilisateurService
        implements CreateUtilisateurUseCase,
                   GetAllUtilisateursUseCase,
                   GetUtilisateurByIdUseCase,
                   UpdateUtilisateurUseCase,
                   DeleteUtilisateurUseCase,
                   ChangeUtilisateurStatusUseCase,
                   ResetUtilisateurPasswordUseCase {

    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurRules utilisateurRules;

    private final CurrentUserProvider currentUserProvider;

    public UtilisateurService(
            UtilisateurRepository utilisateurRepository,
            UtilisateurRules utilisateurRules,
            CurrentUserProvider currentUserProvider) {

        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurRules = utilisateurRules;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    @Transactional
    public UtilisateurResponse create(CreateUtilisateurRequest request) {

        Utilisateur utilisateur = new Utilisateur();

        utilisateurRules.applyUsername(utilisateur, request.getUsername());
        utilisateurRules.applyPassword(utilisateur, request.getPassword());
        utilisateurRules.applyPersonne(utilisateur, request.getPersonneId());

        utilisateur.setRole(requireRole(request.getRole()));
        utilisateur.setActif(true);
        utilisateur.setDateCreation(LocalDateTime.now());

        return UtilisateurResponse.from(utilisateurRepository.save(utilisateur));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UtilisateurResponse> getAll(String query, Role role, Boolean actif) {

        return utilisateurRepository.findAll()
                .stream()
                .filter(utilisateur -> role == null || role == utilisateur.getRole())
                .filter(utilisateur -> actif == null || actif.equals(utilisateur.getActif()))
                .filter(utilisateur -> matches(utilisateur, query))
                .sorted(Comparator.comparing(Utilisateur::getDateCreation).reversed())
                .map(UtilisateurResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UtilisateurResponse getById(Long id) {

        return UtilisateurResponse.from(findUtilisateur(id));
    }

    @Override
    @Transactional
    public UtilisateurResponse update(Long id, UpdateUtilisateurRequest request) {

        Utilisateur utilisateur = findUtilisateur(id);
        Role role = requireRole(request.getRole());

        if (isCurrentUser(utilisateur) && role != Role.ADMIN) {
            throw new IllegalArgumentException(
                    "Vous ne pouvez pas retirer votre propre rôle d'administrateur.");
        }

        utilisateurRules.applyUsername(utilisateur, request.getUsername());
        utilisateurRules.applyPersonne(utilisateur, request.getPersonneId());
        utilisateur.setRole(role);

        return UtilisateurResponse.from(utilisateurRepository.save(utilisateur));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Utilisateur utilisateur = findUtilisateur(id);

        if (isCurrentUser(utilisateur)) {
            throw new IllegalArgumentException("Vous ne pouvez pas supprimer votre propre compte.");
        }

        utilisateurRepository.deleteById(utilisateur.getId());
    }

    @Override
    @Transactional
    public UtilisateurResponse changeStatus(Long id, Boolean actif) {

        if (actif == null) {
            throw new IllegalArgumentException("Le statut du compte est obligatoire.");
        }

        Utilisateur utilisateur = findUtilisateur(id);

        if (isCurrentUser(utilisateur) && !actif) {
            throw new IllegalArgumentException("Vous ne pouvez pas désactiver votre propre compte.");
        }

        utilisateur.setActif(actif);

        return UtilisateurResponse.from(utilisateurRepository.save(utilisateur));
    }

    @Override
    @Transactional
    public void resetPassword(Long id, ResetPasswordRequest request) {

        Utilisateur utilisateur = findUtilisateur(id);

        utilisateurRules.applyPassword(utilisateur, request == null ? null : request.getMotDePasse());

        utilisateurRepository.save(utilisateur);
    }

    private Utilisateur findUtilisateur(Long id) {

        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
    }

    private boolean isCurrentUser(Utilisateur utilisateur) {

        return utilisateur.getId().equals(currentUserProvider.getCurrentUser().getId());
    }

    private static Role requireRole(Role role) {

        if (role == null) {
            throw new IllegalArgumentException("Le rôle du compte est obligatoire.");
        }

        return role;
    }

    private static boolean matches(Utilisateur utilisateur, String query) {

        Personne personne = utilisateur.getPersonne();

        return SearchText.matches(query,
                utilisateur.getUsername(),
                personne != null ? personne.getNom() : null,
                personne != null ? personne.getPrenom() : null);
    }
}
