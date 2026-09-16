package com.audit.audit_document.infrastructure.adapter;
import com.audit.audit_document.infrastructure.jpa.MissionPersonneJpa;
import com.audit.audit_document.domain.entity.MissionPersonne;
import com.audit.audit_document.domain.repository.MissionPersonneRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MissionPersonneRepositoryAdapter
        implements MissionPersonneRepository {

    private final MissionPersonneJpa repository;

    public MissionPersonneRepositoryAdapter(
            MissionPersonneJpa repository) {
        this.repository = repository;
    }

    @Override
    public MissionPersonne save(MissionPersonne missionPersonne) {
        return repository.save(missionPersonne);
    }

    @Override
    public Optional<MissionPersonne> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<MissionPersonne> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MissionPersonne> findByMissionId(Long missionId) {
    return repository.findByMissionId(missionId);
    }

    @Override
    public void deleteByMissionId(Long missionId) {
    repository.deleteByMissionId(missionId);
    repository.flush();
    }
}