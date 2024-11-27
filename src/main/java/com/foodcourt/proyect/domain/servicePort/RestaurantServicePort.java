package com.foodcourt.proyect.domain.servicePort;


import com.foodcourt.proyect.infrastructure.dto.ListRestaurantDTO;

import java.util.List;

public interface RestaurantServicePort {

    public Restaurant createRestaurant(Restaurant restaurant);

    List<ListRestaurantDTO> getAllRestaurants(long cantidad);
}
