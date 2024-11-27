package com.foodcourt.proyect.infrastructure.persistence.jpa.repository;

import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    Stream<RestaurantEntity> findByOwnerId(Long ownerId);
}
