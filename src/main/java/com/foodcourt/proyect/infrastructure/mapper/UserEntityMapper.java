package com.foodcourt.proyect.infrastructure.mapper;

import com.foodcourt.proyect.domain.model.User;
import com.foodcourt.proyect.infrastructure.common.MapperBase;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper extends MapperBase<UserEntity, User> {
}
