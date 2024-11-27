package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private Long id;
    private Long restaurantId;
    private Long clientId;
    private String plateList;
    private String plateQuantity;
    private OrderStatus status;
    private Long assignedEmployee;
    private int securityPin;

    public OrderDTO(Long id, Long restaurantId, Long clientId, String plateList, String plateQuantity, OrderStatus status, Long assignedEmployee, int securityPin) {
        this.id = id;
        this.clientId = clientId;
        this.plateList = plateList;
        this.plateQuantity = plateQuantity;
        this.restaurantId = restaurantId;
        this.status = status;
        this.assignedEmployee = assignedEmployee;
        this.securityPin = securityPin;

    }

    public OrderDTO() {
    }

    public OrderDTO(Long id) {
        this.id = id;
    }

    public OrderDTO(Long id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }
}
