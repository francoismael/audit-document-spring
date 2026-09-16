package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneJpa
        extends JpaRepository<Personne, Long> {
}