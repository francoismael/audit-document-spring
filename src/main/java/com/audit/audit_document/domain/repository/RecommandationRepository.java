package com.audit.audit_document.domain.repository;

import java.util.List;
import java.util.Optional;

import com.audit.audit_document.domain.entity.Recommandation;

public interface RecommandationRepository {

    Recommandation save(Recommandation recommandation);

    Optional<Recommandation> findById(Long id);

    List<Recommandation> findByConstatId(Long constatId);

    void deleteById(Long id);

    void deleteByConstatId(Long constatId);
}