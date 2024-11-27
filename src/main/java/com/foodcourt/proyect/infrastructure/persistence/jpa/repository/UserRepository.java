package com.foodcourt.proyect.infrastructure.persistence.jpa.repository;

import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByMail(String mail);
}
