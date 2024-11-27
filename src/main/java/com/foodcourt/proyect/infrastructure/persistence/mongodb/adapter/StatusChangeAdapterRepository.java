package com.foodcourt.proyect.infrastructure.persistence.mongodb.adapter;

import com.foodcourt.proyect.domain.repositoryPort.StatusChangePersistencePort;
import com.foodcourt.proyect.infrastructure.mapper.StatusChangeEntityMapper;
import com.foodcourt.proyect.infrastructure.persistence.mongodb.repository.StatusChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StatusChangeAdapterRepository implements StatusChangePersistencePort {

    private final StatusChangeRepository statusChangeRepository;
    private final StatusChangeEntityMapper statusChangeEntityMapper;

    @Override
    public void registerStatusChange(StatusChange statusChange) {
        statusChangeRepository.save(statusChangeEntityMapper.BToA(statusChange));
    }

    @Override
    public List<StatusChange> getAll() {
        return statusChangeEntityMapper.AToB(statusChangeRepository.findAll().stream()).collect(Collectors.toList());
    }
}
