package com.foodcourt_users.proyect.infrastructure.ApiClient;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestaurantApiClient {

    private final WebClient webClient;

    public RestaurantApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void updateEmployeeOnRestaurant(Long restaurantId) {
        webClient.patch().uri("/foodcourt/addRestaurantEmployee/" + restaurantId);
    }
}