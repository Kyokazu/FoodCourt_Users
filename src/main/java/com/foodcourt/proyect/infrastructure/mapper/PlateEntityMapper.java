package com.foodcourt.proyect.infrastructure.mapper;

import com.foodcourt.proyect.infrastructure.common.MapperBase;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.PlateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlateEntityMapper extends MapperBase<PlateEntity, Plate>{
}
