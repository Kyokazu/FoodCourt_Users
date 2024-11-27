package com.foodcourt.proyect.infrastructure.mapper;

import com.foodcourt.proyect.infrastructure.common.MapperBase;
import com.foodcourt.proyect.infrastructure.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDTOMapper extends MapperBase<OrderDTO, Order> {
}
