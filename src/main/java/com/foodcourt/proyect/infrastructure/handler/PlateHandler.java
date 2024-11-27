package com.foodcourt.proyect.infrastructure.handler;

import com.foodcourt.proyect.domain.servicePort.PlateServicePort;
import com.foodcourt.proyect.infrastructure.dto.ListPlateDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;
import com.foodcourt.proyect.infrastructure.dto.PlateDTO;
import com.foodcourt.proyect.infrastructure.mapper.PlateDTOMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateHandler {

    private final PlateDTOMapper plateDTOMapper;
    private final PlateServicePort createPlateServicePort;
    private final PlateServicePort updatePlateServicePort;
    private final PlateServicePort ableUnablePlateServicePort;
    private final PlateServicePort listPlateServicePort;

    public PlateHandler(@Qualifier("createPlate") PlateServicePort createPlateServicePort,
                        PlateDTOMapper plateDTOMapper,
                        @Qualifier("updatePlate") PlateServicePort updatePlateServicePort,
                        @Qualifier("enableUnablePlate") PlateServicePort enableUnablePlateServicePort,
                        @Qualifier("listPlate") PlateServicePort listPlateServicePort) {
        this.createPlateServicePort = createPlateServicePort;
        this.plateDTOMapper = plateDTOMapper;
        this.updatePlateServicePort = updatePlateServicePort;
        this.ableUnablePlateServicePort = enableUnablePlateServicePort;
        this.listPlateServicePort = listPlateServicePort;
    }

    public PlateDTO createPlate(PlateDTO plateDTO) {
        return plateDTOMapper.BToA(createPlateServicePort.createPlate(plateDTOMapper.AToB(plateDTO)));
    }

    public PlateDTO updatePriceOrDescription(PlateDTO plateDTO) {
        return plateDTOMapper.BToA(updatePlateServicePort.updateFields(plateDTOMapper.AToB(plateDTO)));
    }

    public PlateDTO enableUnablePlate(PlateDTO plateDTO) {
        return plateDTOMapper.BToA(ableUnablePlateServicePort.ableUnablePlate(plateDTOMapper.AToB(plateDTO)));
    }

    public List<ListPlateDTO> listPlate(PageDTO pageDTO) {
        return listPlateServicePort.listPlate(pageDTO);
    }


}
