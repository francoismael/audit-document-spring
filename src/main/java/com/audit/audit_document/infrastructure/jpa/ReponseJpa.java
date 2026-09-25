package com.audit.audit_document.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audit.audit_document.domain.entity.Reponse;

public interface ReponseJpa
        extends JpaRepository<Reponse, Long> {

    List<Reponse> findByRecommandationId(
            Long recommandationId
    );
}