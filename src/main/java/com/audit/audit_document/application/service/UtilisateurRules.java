package com.audit.audit_document.application.service;

import com.audit.audit_document.domain.entity.Personne;
import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.exception.ConflictException;
import com.audit.audit_document.domain.exception.ResourceNotFoundException;
import com.audit.audit_document.domain.repository.PersonneRepository;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

/*
 * Checks shared by the sign-up, the account administration and the password change.
 */
@Component
public class UtilisateurRules {

    private static final Pattern USERNAME = Pattern.compile("[A-Za-z0-9._-]{3,100}");

    private static final int PASSWORD_MIN_LENGTH = 8;

    private final UtilisateurRepository utilisateurRepository;

    private final PersonneRepository personneRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UtilisateurRules(
            UtilisateurRepository utilisateurRepository,
            PersonneRepository personneRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.utilisateurRepository = utilisateurRepository;
        this.personneRepository = personneRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Trims and checks the username, which must not belong to another account.
    public void applyUsername(Utilisateur utilisateur, String username) {

        String cleanUsername = username == null ? "" : username.trim();

        if (!USERNAME.matcher(cleanUsername).matches()) {
            throw new IllegalArgumentException(
                    "Le nom d'utilisateur doit contenir de 3 à 100 caractères : lettres, chiffres, point, tiret ou souligné.");
        }

        if (isTakenByAnother(utilisateurRepository.findByUsername(cleanUsername), utilisateur)) {
            throw new ConflictException("Ce nom d'utilisateur existe déjà");
        }

        utilisateur.setUsername(cleanUsername);
    }

    public void applyPassword(Utilisateur utilisateur, String password) {

        if (password == null
                || password.length() < PASSWORD_MIN_LENGTH
                || !password.matches(".*[A-Za-z].*")
                || !password.matches(".*\\d.*")) {

            throw new IllegalArgumentException(
                    "Le mot de passe doit contenir au moins 8 caractères, dont une lettre et un chiffre.");
        }

        utilisateur.setPassword(passwordEncoder.encode(password));
    }

    public boolean matchesPassword(Utilisateur utilisateur, String password) {

        return password != null && passwordEncoder.matches(password, utilisateur.getPassword());
    }

    // A person of the directory belongs to one account at most; null removes the link.
    public void applyPersonne(Utilisateur utilisateur, Long personneId) {

        if (personneId == null) {
            utilisateur.setPersonne(null);
            return;
        }

        Personne personne = personneRepository
                .findById(personneId)
                .orElseThrow(() -> new ResourceNotFoundException("Personne introuvable : " + personneId));

        if (isTakenByAnother(utilisateurRepository.findByPersonneId(personneId), utilisateur)) {
            throw new ConflictException(
                    "Cette personne est déjà reliée à un autre compte");
        }

        utilisateur.setPersonne(personne);
    }

    private static boolean isTakenByAnother(Optional<Utilisateur> owner, Utilisateur utilisateur) {

        return owner.isPresent() && !owner.get().getId().equals(utilisateur.getId());
    }
}
