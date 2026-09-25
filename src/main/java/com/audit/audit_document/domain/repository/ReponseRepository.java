package com.audit.audit_document.domain.repository;

import java.util.List;
import java.util.Optional;

import com.audit.audit_document.domain.entity.Reponse;

public interface ReponseRepository {

    Reponse save(Reponse reponse);

    Optional<Reponse> findById(Long id);

    List<Reponse> findAll();

    List<Reponse> findByRecommandationId(
            Long recommandationId
    );

    void deleteById(Long id);
}