package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.RapportAudit;
import com.audit.audit_document.domain.repository.RapportAuditRepository;
import com.audit.audit_document.infrastructure.jpa.RapportAuditJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RapportAuditRepositoryAdapter
        implements RapportAuditRepository {

    private final RapportAuditJpa rapportAuditJpa;

    public RapportAuditRepositoryAdapter(
            RapportAuditJpa rapportAuditJpa) {

        this.rapportAuditJpa = rapportAuditJpa;
    }

    @Override
    public RapportAudit save(RapportAudit rapportAudit) {

        return rapportAuditJpa.save(rapportAudit);
    }

    @Override
    public Optional<RapportAudit> findById(Long id) {

        return rapportAuditJpa.findById(id);
    }

    @Override
    public Optional<RapportAudit> findByMissionId(Long missionId) {

        return rapportAuditJpa.findByMissionId(missionId);
    }

    @Override
    public List<RapportAudit> findAll() {

        return rapportAuditJpa.findAll();
    }

    @Override
    public void deleteById(Long id) {

        rapportAuditJpa.deleteById(id);
    }
}