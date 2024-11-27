package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO {

    private long page;
    private long restaurantId;
    private String category;
}
