package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonneJpa
        extends JpaRepository<Personne, Long> {

    @Query(
        value = "SELECT nextval('personne_matricule_seq')",
        nativeQuery = true
    )
    Long getNextMatriculeNumber();

    boolean existsByMatricule(String matricule);
}
