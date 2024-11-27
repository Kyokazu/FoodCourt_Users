package com.foodcourt.proyect.infrastructure.persistence.jpa.adapter;

import com.foodcourt.proyect.domain.repositoryPort.PlatePersistencePort;
import com.foodcourt.proyect.infrastructure.mapper.PlateEntityMapper;
import com.foodcourt.proyect.infrastructure.persistence.jpa.repository.PlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlateAdapterRepository implements PlatePersistencePort {

    private final PlateRepository plateRepository;
    private final PlateEntityMapper plateEntityMapper;

    @Override
    public Plate findById(Long aLong) {
        return plateEntityMapper.AToB(plateRepository.findById(aLong).get());
    }

    @Override
    public List<Plate> findAll() {
        return plateEntityMapper.AToB(plateRepository.findAll().stream()).collect(Collectors.toList());
    }


    @Override
    public Plate save(Plate entity) {
        return plateEntityMapper.AToB(plateRepository.save(plateEntityMapper.BToA(entity)));
    }

    @Override
    public void update(Plate entity) {
        plateEntityMapper.AToB(plateRepository.save(plateEntityMapper.BToA(entity)));
    }


    public void delete(Plate entity) {
        plateRepository.delete(plateEntityMapper.BToA(entity));
    }
}
