package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.PlatePersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.servicePort.PlateServicePort;
import com.foodcourt.proyect.infrastructure.dto.ListPlateDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@RequiredArgsConstructor
@Qualifier("createPlate")
public class CreatePlateUseCase implements PlateServicePort {

    private final PlatePersistencePort platePersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;

    @Override
    public Plate createPlate(Plate plate) {
        if (!validateExistentRestaurant(plate.getRestaurantId())) {
            throw new RestauranteInexistenteException();
        }
        if (!validatePrice(plate.getPrice())) {
            throw new PrecioInvalidoException();
        }
        return platePersistencePort.save(plate);
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
        return List.of();
    }

    private boolean validateExistentRestaurant(Long id) {
        return restaurantPersistencePort.findById(id) != null;
    }

    private boolean validatePrice(int price) {
        return price > 0;
    }

}
