package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StatusChangeDTO {

    private String id;
    private Long clientId;
    private Long orderId;
    private String status;
    private Date changeDate;
}
