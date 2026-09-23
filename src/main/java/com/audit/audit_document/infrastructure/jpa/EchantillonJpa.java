package com.audit.audit_document.infrastructure.jpa;

import com.audit.audit_document.domain.entity.Echantillon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EchantillonJpa
        extends JpaRepository<Echantillon, Long> {

    List<Echantillon> findByTestId(Long testId);

    void deleteByTestId(Long testId);

    @Query(
        value = "SELECT COALESCE(MAX(numero), 0) + 1 " +
                "FROM echantillon " +
                "WHERE test_id = :testId",
        nativeQuery = true
    )
    Integer getNextNumero(
            @Param("testId") Long testId
    );

    @Query(
        value = "SELECT nextval('echantillon_reference_seq')",
        nativeQuery = true
    )
    Long getNextReference();
}