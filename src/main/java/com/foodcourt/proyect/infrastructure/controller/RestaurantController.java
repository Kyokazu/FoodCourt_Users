package com.foodcourt.proyect.infrastructure.controller;

import com.foodcourt.proyect.infrastructure.dto.ListRestaurantDTO;
import com.foodcourt.proyect.infrastructure.dto.PageDTO;
import com.foodcourt.proyect.infrastructure.dto.RestaurantDTO;
import com.foodcourt.proyect.infrastructure.handler.RestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantHandler restaurantHandler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createRestaurant")
    public ResponseEntity<RestaurantDTO> createOwner(@RequestBody RestaurantDTO restaurantDTO) {
        return new ResponseEntity<>(restaurantHandler.createRestaurant(restaurantDTO), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/listRestaurant")
    public ResponseEntity<List<ListRestaurantDTO>> listRestaurants(@RequestBody PageDTO page) {
        return new ResponseEntity<List<ListRestaurantDTO>>(restaurantHandler.listRestaurant(page.getPage()), HttpStatus.ACCEPTED);
    }
}
