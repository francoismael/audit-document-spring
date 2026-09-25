package com.audit.audit_document.infrastructure.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audit.audit_document.domain.entity.Risque;

public interface RisqueJpa extends JpaRepository<Risque, Long> {

    List<Risque> findByConstatId(Long constatId);

    void deleteByConstatId(Long constatId);
}