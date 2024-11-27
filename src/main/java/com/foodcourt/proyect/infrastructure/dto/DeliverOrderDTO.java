package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class DeliverOrderDTO {
    private Long orderId;
    private int securityPin;
}
