package com.foodcourt.proyect.infrastructure.mapper;

import com.foodcourt.proyect.infrastructure.common.MapperBase;
import com.foodcourt.proyect.infrastructure.dto.StatusChangeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusChangeDTOMapper extends MapperBase<StatusChangeDTO, StatusChange> {
}
