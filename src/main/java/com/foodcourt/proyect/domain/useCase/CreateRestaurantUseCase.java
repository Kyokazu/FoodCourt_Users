package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.exception.*;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.UserPersistencePort;
import com.foodcourt.proyect.domain.servicePort.RestaurantServicePort;
import com.foodcourt.proyect.infrastructure.dto.ListRestaurantDTO;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class CreateRestaurantUseCase implements RestaurantServicePort {

    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserPersistencePort userPersistencePort;


    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurantCreationValidations(restaurant);
        restaurant.setOwnerId(getOwnerId());
        return restaurantPersistencePort.save(restaurant);
    }

    @Override
    public List<ListRestaurantDTO> getAllRestaurants(long cantidad) {
        return List.of();
    }

    private void restaurantCreationValidations(Restaurant entity) {
        Long ownerId = getOwnerId();
        if (!existentUser(ownerId)) {
            throw new UsuarioInexistenteException();
        }
        if (!validatePhone(entity.getPhone())) {
            throw new CelularNoValidoException();
        }
        if (!validateNit(entity.getNit())) {
            throw new NitNoNumericoException();
        }
        if (!validateRestaurantName(entity.getName())) {
            throw new NombreRestauranteException();
        }

    }

    private boolean existentUser(Long aLong) {
        return userPersistencePort.findById(aLong) != null;
    }

    private boolean validatePhone(String phone) {
        return phone == null || phone.matches("\\+?\\d{1,13}");
    }

    private boolean validateNit(String nit) {
        return nit == null || nit.matches("\\d+");
    }

    private boolean validateRestaurantName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NombreRestauranteException();
        }
        if (name.matches("\\d+")) {
            throw new NombreRestauranteException();
        }
        if (!name.matches(".*[a-zA-Z]+.*")) {
            throw new NombreRestauranteException();
        }
        return true;
    }

    private Long getOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }
}
