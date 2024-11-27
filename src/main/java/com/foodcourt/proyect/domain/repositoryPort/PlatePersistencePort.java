package com.foodcourt.proyect.domain.repositoryPort;

import java.util.List;

public interface PlatePersistencePort {
    Plate findById(Long aLong);

    List<Plate> findAll();

    Plate save(Plate entity);

    void update(Plate entity);
}
