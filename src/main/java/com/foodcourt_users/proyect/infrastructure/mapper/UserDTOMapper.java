package com.foodcourt_users.proyect.infrastructure.mapper;

import com.foodcourt_users.proyect.domain.model.User;
import com.foodcourt_users.proyect.infrastructure.common.MapperBase;
import com.foodcourt_users.proyect.infrastructure.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper extends MapperBase<UserDTO, User> {

}
