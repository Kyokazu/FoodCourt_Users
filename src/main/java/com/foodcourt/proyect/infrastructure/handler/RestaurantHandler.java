package com.foodcourt.proyect.infrastructure.handler;

import com.foodcourt.proyect.domain.servicePort.RestaurantServicePort;
import com.foodcourt.proyect.infrastructure.dto.ListRestaurantDTO;
import com.foodcourt.proyect.infrastructure.dto.RestaurantDTO;
import com.foodcourt.proyect.infrastructure.mapper.RestaurantDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class RestaurantHandler {

    private final RestaurantDTOMapper restaurantDTOMapper;
    private final RestaurantServicePort restaurantServicePort;
    private final RestaurantServicePort listRestaurantServicePort;

    public RestaurantHandler(@Qualifier("createRestaurant") RestaurantServicePort restaurantServicePort,
                             @Qualifier("listRestaurant") RestaurantServicePort listRestaurantServicePort,
                             RestaurantDTOMapper restaurantDTOMapper) {
        this.listRestaurantServicePort = listRestaurantServicePort;
        this.restaurantServicePort = restaurantServicePort;
        this.restaurantDTOMapper = restaurantDTOMapper;
    }


    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        return restaurantDTOMapper.BToA(restaurantServicePort.createRestaurant(restaurantDTOMapper.AToB(restaurantDTO)));
    }

    public List<ListRestaurantDTO> listRestaurant(long cantidad) {
        return listRestaurantServicePort.getAllRestaurants(cantidad);
    }

}
