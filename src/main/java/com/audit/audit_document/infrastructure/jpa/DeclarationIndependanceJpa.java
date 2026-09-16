package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.DeclarationIndependance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclarationIndependanceJpa extends JpaRepository<DeclarationIndependance, Long>{
    List<DeclarationIndependance> findByMissionId(Long id);
    List<DeclarationIndependance> findByPersonneId(Long id);
}