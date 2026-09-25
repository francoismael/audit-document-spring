package com.audit.audit_document.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audit.audit_document.domain.entity.Recommandation;

public interface RecommandationJpa extends JpaRepository<Recommandation, Long> {

    List<Recommandation> findByConstatId(Long constatId);

    void deleteByConstatId(Long constatId);
}