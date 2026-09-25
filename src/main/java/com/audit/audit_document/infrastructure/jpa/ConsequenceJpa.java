package com.audit.audit_document.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audit.audit_document.domain.entity.Consequence;

public interface ConsequenceJpa extends JpaRepository<Consequence, Long> {

    List<Consequence> findByConstatId(Long constatId);

    void deleteByConstatId(Long constatId);
}