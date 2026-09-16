package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Utilisateur;
import com.audit.audit_document.domain.repository.UtilisateurRepository;
import com.audit.audit_document.infrastructure.jpa.UtilisateurJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepositoryAdapter implements UtilisateurRepository {

    private final UtilisateurJpa utilisateurJpa;

    public UtilisateurRepositoryAdapter(UtilisateurJpa utilisateurJpa) {
        this.utilisateurJpa = utilisateurJpa;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurJpa.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> findById(Long id) {
        return utilisateurJpa.findById(id);
    }

    @Override
    public Optional<Utilisateur> findByUsername(String username) {
        return utilisateurJpa.findByUsername(username);
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        return utilisateurJpa.findByEmail(email);
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurJpa.findAll();
    }

    @Override
    public void deleteById(Long id) {
        utilisateurJpa.deleteById(id);
    }
}