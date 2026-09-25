package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.audit.audit_document.domain.entity.Reponse;
import com.audit.audit_document.domain.repository.ReponseRepository;
import com.audit.audit_document.infrastructure.jpa.ReponseJpa;

@Component
public class ReponseRepositoryAdapter
        implements ReponseRepository {

    private final ReponseJpa reponseJpa;

    public ReponseRepositoryAdapter(
            ReponseJpa reponseJpa) {

        this.reponseJpa = reponseJpa;
    }

    @Override
    public Reponse save(Reponse reponse) {

        return reponseJpa.save(
                reponse
        );
    }

    @Override
    public Optional<Reponse> findById(
            Long id) {

        return reponseJpa.findById(id);
    }

    @Override
    public List<Reponse> findAll() {

        return reponseJpa.findAll();
    }

    @Override
    public List<Reponse> findByRecommandationId(
            Long recommandationId) {

        return reponseJpa.findByRecommandationId(
                recommandationId
        );
    }

    @Override
    public void deleteById(
            Long id) {

        reponseJpa.deleteById(id);
    }
}