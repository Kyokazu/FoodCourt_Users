package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.PlatePersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.servicePort.PlateServicePort;
import com.foodcourt.proyect.infrastructure.dto.ListPlateDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.stream.Collectors;

@Qualifier("listPlate")
@RequiredArgsConstructor
public class ListPlateUseCase implements PlateServicePort {

    private final PlatePersistencePort platePersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;


    @Override
    public Plate createPlate(Plate plate) {
        return null;
    }

    @Override
    public Plate updateFields(Plate plate) {
        return null;
    }

    @Override
    public Plate ableUnablePlate(Plate plate) {
        return null;
    }

    @Override
    public List<ListPlateDTO> listPlate(PageDTO pageDTO) {
        return getAllPlates(pageDTO);
    }

    public List<ListPlateDTO> getAllPlates(PageDTO pageDTO) {
        List<Plate> plate = platePersistencePort.findAll();

        return plate.stream()
                .filter(p -> p.getRestaurantId().equals(pageDTO.getRestaurantId()) && p.getCategory().equalsIgnoreCase(pageDTO.getCategory()))
                .sorted((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()))
                .limit(pageDTO.getPage())
                .map(r -> new ListPlateDTO(r.getCategory(), r.getDescription(), r.getName(), r.getPrice(), r.getUrlImage()))
                .collect(Collectors.toList());
    }

}
