package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class NotificationMessageDTO {

    private String clientPhone;
    private String message;

    public NotificationMessageDTO(String clientPhone, String message) {
        this.clientPhone = clientPhone;
        this.message = message;
    }
}
