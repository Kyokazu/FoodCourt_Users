package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class OrderRankingDTO {

    private Long orderId;
    private Long deliverTime;

    public OrderRankingDTO(Long orderId, Long deliverTime) {
        this.deliverTime = deliverTime;
        this.orderId = orderId;
    }
}
