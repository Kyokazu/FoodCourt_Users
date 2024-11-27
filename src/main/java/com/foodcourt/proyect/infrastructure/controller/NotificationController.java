package com.foodcourt.proyect.infrastructure.controller;

import com.foodcourt.proyect.infrastructure.dto.ClientNotificationDTO;
import com.foodcourt.proyect.infrastructure.dto.NotificationMessageDTO;
import com.foodcourt.proyect.infrastructure.handler.OrderHandler;
import com.foodcourt.proyect.infrastructure.handler.SMSHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/twilio")
@RequiredArgsConstructor
public class NotificationController {

    private final SMSHandler smsHandler;
    private final OrderHandler orderHandler;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/sendSms")
    @Qualifier("notifyReadyOrder")
    public ResponseEntity<String> sendSms(@RequestBody ClientNotificationDTO clientNotificationDTO) {
        NotificationMessageDTO notificationMessageDTO = orderHandler.notifyReadyOrder(clientNotificationDTO);
        smsHandler.sendSms(notificationMessageDTO.getClientPhone(), notificationMessageDTO.getMessage());
        return new ResponseEntity<>(notificationMessageDTO.getMessage(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Qualifier("notifyReadyOrder")
    @PutMapping("/sendWhatsapp")
    public ResponseEntity<String> sendWhatsapp(@RequestBody ClientNotificationDTO clientNotificationDTO) {
        NotificationMessageDTO notificationMessageDTO = orderHandler.notifyReadyOrder(clientNotificationDTO);
        smsHandler.sendWhatsapp(notificationMessageDTO.getClientPhone(), notificationMessageDTO.getMessage());
        return new ResponseEntity<>(notificationMessageDTO.getMessage(), HttpStatus.ACCEPTED);
    }
}