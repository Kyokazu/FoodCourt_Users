package com.foodcourt.proyect.infrastructure.persistence.jpa.repository;

import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlateRepository extends JpaRepository<PlateEntity, Long> {
}
