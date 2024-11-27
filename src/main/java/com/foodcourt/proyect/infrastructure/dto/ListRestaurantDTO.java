package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class ListRestaurantDTO {

    private String name;
    private String urlLogo;

    public ListRestaurantDTO(String name, String urlLogo) {
        this.name = name;
        this.urlLogo = urlLogo;
    }

}
