package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.PvAffirmation;
import com.audit.audit_document.domain.repository.PvAffirmationRepository;
import com.audit.audit_document.infrastructure.jpa.PvAffirmationJpa;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PvAffirmationRepositoryAdapter
        implements PvAffirmationRepository {

    private final PvAffirmationJpa pvAffirmationJpa;

    public PvAffirmationRepositoryAdapter(
            PvAffirmationJpa pvAffirmationJpa) {

        this.pvAffirmationJpa =
                pvAffirmationJpa;
    }

    @Override
    public PvAffirmation save(
            PvAffirmation pvAffirmation) {

        return pvAffirmationJpa.save(
                pvAffirmation
        );
    }

    @Override
    public Optional<PvAffirmation> findById(
            Long id) {

        return pvAffirmationJpa.findById(id);
    }

    @Override
    public Optional<PvAffirmation> findByMissionId(
            Long missionId) {

        return pvAffirmationJpa.findByMissionId(
                missionId
        );
    }

    @Override
    public void deleteById(Long id) {

        pvAffirmationJpa.deleteById(id);
    }
}