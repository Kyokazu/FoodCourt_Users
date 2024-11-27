package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.PlatePersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.UserPersistencePort;
import com.foodcourt.proyect.domain.servicePort.PlateServicePort;
import com.foodcourt.proyect.infrastructure.dto.ListPlateDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Qualifier("updatePlate")
public class UpdatePlateUseCase implements PlateServicePort {
    private final PlatePersistencePort platePersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserPersistencePort userPersistencePort;


    @Override
    public Plate createPlate(Plate plate) {
        return null;
    }

    @Override
    public Plate updateFields(Plate plate) {
        if (!validateExistentRestaurant(plate.getRestaurantId())) {
            throw new RestauranteInexistenteException();
        }
        if (!verifyExistingPlate(plate.getId())) {
            throw new PlatoInexistenteException();
        }

        if (!verifyUpdateFields(plate)) {
            throw new CamposPlatosException();
        }
        if (!validatePlateOwner(plate.getRestaurantId())) {
            throw new PropietarioDePlatoInvalidoException();
        }
        platePersistencePort.update(plate);
        return plate;
    }

    @Override
    public Plate ableUnablePlate(Plate plate) {
        return null;
    }

    @Override
    public List<ListPlateDTO> listPlate(PageDTO pageDTO) {
        return List.of();
    }

    private boolean verifyUpdateFields(Plate plate) {
        return Objects.equals(plate.getDescription(), platePersistencePort.findById(plate.getId()).getDescription())
                && !Objects.equals(plate.getPrice(), platePersistencePort.findById(plate.getId()).getPrice());
    }

    private boolean verifyExistingPlate(Long id) {
        return platePersistencePort.findById(id) != null;
    }

    private boolean validateExistentRestaurant(Long id) {
        return restaurantPersistencePort.findById(id) != null;
    }

    private boolean validatePlateOwner(Long plateId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Plate plate = platePersistencePort.findById(plateId);
        Long userId = userPersistencePort.findIdByMail(user.getMail());
        return restaurantPersistencePort.findOwnerIdByRestaurantId(plate.getRestaurantId()).equals(userId);
    }
}
