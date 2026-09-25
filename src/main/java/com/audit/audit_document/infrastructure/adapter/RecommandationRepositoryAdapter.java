package com.audit.audit_document.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.audit.audit_document.domain.entity.Recommandation;
import com.audit.audit_document.domain.repository.RecommandationRepository;
import com.audit.audit_document.infrastructure.jpa.RecommandationJpa;

@Component
public class RecommandationRepositoryAdapter implements RecommandationRepository {

    private final RecommandationJpa recommandationJpa;

    public RecommandationRepositoryAdapter(
            RecommandationJpa recommandationJpa) {
        this.recommandationJpa = recommandationJpa;
    }

    @Override
    public Recommandation save(Recommandation recommandation) {
        return recommandationJpa.save(recommandation);
    }

    @Override
    public Optional<Recommandation> findById(Long id) {
        return recommandationJpa.findById(id);
    }

    @Override
    public List<Recommandation> findByConstatId(Long constatId) {
        return recommandationJpa.findByConstatId(constatId);
    }

    @Override
    public void deleteById(Long id) {
        recommandationJpa.deleteById(id);
    }

    @Override
    public void deleteByConstatId(Long constatId) {
        recommandationJpa.deleteByConstatId(constatId);
    }
}