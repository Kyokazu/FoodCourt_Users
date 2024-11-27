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

@RequiredArgsConstructor
@Qualifier("enableUnablePlate")
public class EnableUnablePlateUseCase implements PlateServicePort {

    private final PlatePersistencePort platePersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserPersistencePort userPersistencePort;

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
        if (!validateExistentPlate(plate.getId())) {
            throw new PlatoInexistenteException();
        }

        if (!validatePlateOwner(plate.getId())) {
            throw new PropietarioDePlatoInvalidoException();
        }
        Plate oldPlate = platePersistencePort.findById(plate.getId());
        oldPlate.setActive(!oldPlate.isActive());
        platePersistencePort.update(oldPlate);
        return oldPlate;
    }

    @Override
    public List<ListPlateDTO> listPlate(PageDTO pageDTO) {
        return List.of();
    }

    private boolean validatePlateOwner(Long plateId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        Plate plate = platePersistencePort.findById(plateId);
        Long userId = userPersistencePort.findIdByMail(user.getMail());
        return restaurantPersistencePort.findOwnerIdByRestaurantId(plate.getRestaurantId()).equals(userId);
    }

    private boolean validateExistentPlate(Long id) {
        return platePersistencePort.findById(id) != null;
    }

}
