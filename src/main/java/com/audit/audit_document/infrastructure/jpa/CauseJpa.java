package com.audit.audit_document.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audit.audit_document.domain.entity.Cause;

public interface CauseJpa extends JpaRepository<Cause, Long> {

    List<Cause> findByConstatId(Long constatId);

    void deleteByConstatId(Long constatId);
}