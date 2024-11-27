package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class ListPlateDTO {

    private String name;
    private int price;
    private String description;
    private String urlImage;
    private String category;

    public ListPlateDTO(String category, String description, String name, int price, String urlImage) {
        this.category = category;
        this.description = description;
        this.name = name;
        this.price = price;
        this.urlImage = urlImage;
    }
}
