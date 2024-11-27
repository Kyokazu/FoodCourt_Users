package com.foodcourt.proyect.domain.servicePort;

import com.foodcourt.proyect.infrastructure.dto.ListPlateDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;

import java.util.List;

public interface PlateServicePort {

    public Plate createPlate(Plate plate);

    public Plate updateFields(Plate plate);

    public Plate ableUnablePlate(Plate plate);

    List<ListPlateDTO> listPlate(PageDTO pageDTO);
}
