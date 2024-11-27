package com.foodcourt.proyect.infrastructure.mapper;

import com.foodcourt.proyect.infrastructure.common.MapperBase;
import com.foodcourt.proyect.infrastructure.persistence.mongodb.entity.StatusChangeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusChangeEntityMapper extends MapperBase<StatusChangeEntity, StatusChange> {

}
