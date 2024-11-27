package com.foodcourt.proyect.infrastructure.handler;


import com.foodcourt.proyect.domain.servicePort.StatusChangeServicePort;
import com.foodcourt.proyect.infrastructure.dto.EmployeeRankingDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderRankingDTO;
import com.foodcourt.proyect.infrastructure.dto.StatusChangeDTO;
import com.foodcourt.proyect.infrastructure.mapper.StatusChangeDTOMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrazabilityHandler {

    private final StatusChangeServicePort statusChangeServicePort;
    private final StatusChangeDTOMapper statusChangeDTOMapper;
    private final StatusChangeServicePort orderEfficiencyServicePort;


    public TrazabilityHandler(@Qualifier("statusChange") StatusChangeServicePort statusChangeServicePort,
                              StatusChangeDTOMapper statusChangeDTOMapper,
                              @Qualifier("orderEfficiency") StatusChangeServicePort orderEfficiencyServicePort) {
        this.statusChangeServicePort = statusChangeServicePort;
        this.statusChangeDTOMapper = statusChangeDTOMapper;
        this.orderEfficiencyServicePort = orderEfficiencyServicePort;
    }

    public List<StatusChangeDTO> getOrdersInfo() {
        return statusChangeDTOMapper.BToA(statusChangeServicePort.checkOrderChangelog().stream()).collect(Collectors.toList());
    }

    public List<OrderRankingDTO> getOrderRanking() {
        return orderEfficiencyServicePort.getOrderRanking();
    }


    public List<EmployeeRankingDTO> getEmployeeRanking() {
        return orderEfficiencyServicePort.getEmployeeRanking();
    }


}
