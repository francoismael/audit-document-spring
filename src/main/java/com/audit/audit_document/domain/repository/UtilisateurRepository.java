package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository {

    Utilisateur save(Utilisateur utilisateur);

    Optional<Utilisateur> findById(Long id);

    Optional<Utilisateur> findByUsername(String username);

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findAll();

    void deleteById(Long id);
}