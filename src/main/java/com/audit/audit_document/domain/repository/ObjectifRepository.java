package com.audit.audit_document.domain.repository;

import com.audit.audit_document.domain.entity.Objectif;

import java.util.List;

public interface ObjectifRepository {

    Objectif save(Objectif objectif);

    List<Objectif> findByTdrId(Long tdrId);

    void deleteByTdrId(Long tdrId);

    void flush();

    Integer getNextNumero(Long tdrId);
}