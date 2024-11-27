package com.foodcourt.proyect.infrastructure.persistence.jpa.repository;

import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
