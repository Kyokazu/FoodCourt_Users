package com.foodcourt.proyect.infrastructure.persistence.mongodb.repository;

import com.foodcourt.proyect.infrastructure.persistence.mongodb.entity.StatusChangeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusChangeRepository extends MongoRepository<StatusChangeEntity, String> {
}
