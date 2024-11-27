package com.foodcourt.proyect.infrastructure.dto;


import lombok.Data;

@Data
public class PlateDTO {
    private Long id;
    private String name;
    private int price;
    private String description;
    private String urlImage;
    private String category;
    private Long restaurantId;
    private boolean active;
}
