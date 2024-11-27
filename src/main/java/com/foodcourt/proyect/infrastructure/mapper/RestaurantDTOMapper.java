package com.foodcourt.proyect.infrastructure.mapper;

import com.foodcourt.proyect.infrastructure.common.MapperBase;
import com.foodcourt.proyect.infrastructure.dto.RestaurantDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantDTOMapper extends MapperBase<RestaurantDTO, Restaurant> {
}
