package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.ReunionPersonne;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReunionPersonneJpa
        extends JpaRepository<ReunionPersonne, Long> {

    List<ReunionPersonne> findByReunionId(Long reunionId);

    void deleteByReunionId(Long reunionId);
}