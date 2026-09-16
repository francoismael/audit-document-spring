package com.audit.audit_document.infrastructure.adapter;

import com.audit.audit_document.domain.entity.Mission;
import com.audit.audit_document.domain.repository.MissionRepository;
import org.springframework.stereotype.Repository;
import com.audit.audit_document.infrastructure.jpa.MissionJpa;
import java.util.List;
import java.util.Optional;

@Repository
public class MissionRepositoryAdapter implements MissionRepository {

    private final MissionJpa repository;

    public MissionRepositoryAdapter(MissionJpa repository) {
        this.repository = repository;
    }

    @Override
    public Mission save(Mission mission) {
        return repository.save(mission);
    }

    @Override
    public Optional<Mission> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Mission> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long getNextNumero() {
    return repository.getNextNumero();
    }
}