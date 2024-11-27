package com.foodcourt.proyect.infrastructure.persistence.jpa.adapter;

import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.infrastructure.mapper.RestaurantEntityMapper;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.RestaurantEntity;
import com.foodcourt.proyect.infrastructure.persistence.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RestaurantAdapterRepository implements RestaurantPersistencePort {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public Restaurant findById(Long aLong) {
        return restaurantEntityMapper.AToB(restaurantRepository.findById(aLong).get());
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantEntityMapper.AToB(restaurantRepository.findAll().stream()).collect(Collectors.toList());
    }

    @Override
    public Restaurant save(Restaurant entity) {
        return restaurantEntityMapper.AToB(restaurantRepository.save(restaurantEntityMapper.BToA(entity)));
    }

    @Override
    public void update(Restaurant entity) {
        restaurantRepository.save(restaurantEntityMapper.BToA(entity));
    }

    public void delete(Restaurant entity) {
        restaurantRepository.delete(restaurantEntityMapper.BToA(entity));
    }

    @Override
    public Long findOwnerIdByRestaurantId(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).get().getOwnerId();
    }

    @Override
    public Restaurant findRestaurantByOwnerId(Long ownerId) {
        List<RestaurantEntity> restaurantes = (restaurantRepository.findAll().stream()
                .filter(r -> r.getOwnerId().equals(ownerId))
                .collect(Collectors.toList()));
        if (!restaurantes.isEmpty()) {
            return restaurantEntityMapper.AToB(restaurantes.get(0));
        }
        return null;
    }

}
