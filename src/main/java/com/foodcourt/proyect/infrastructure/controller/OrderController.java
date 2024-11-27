package com.foodcourt.proyect.infrastructure.controller;


import com.foodcourt.proyect.infrastructure.dto.DeliverOrderDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderListDTO;
import com.foodcourt.proyect.infrastructure.handler.OrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderHandler orderHandler;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/createOrder")
    @Qualifier("createOrder")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(orderHandler.createOrder(order), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/listOrders")
    @Qualifier("listOrders")
    public ResponseEntity<List<OrderDTO>> listOrders(@RequestBody OrderListDTO order) {
        return new ResponseEntity<>(orderHandler.listOrders(order), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/assignOrder")
    @Qualifier("assignOrder")
    public ResponseEntity<OrderDTO> assignOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(orderHandler.assignOrder(order), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/deliverOrder")
    @Qualifier("deliverOrder")
    public ResponseEntity<String> deliverOrder(@RequestBody DeliverOrderDTO deliverOrderDTO) {
        return new ResponseEntity<>(orderHandler.deliverOrder(deliverOrderDTO).getMessage(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/cancelOrder")
    @Qualifier("cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestBody OrderDTO order) {
        return new ResponseEntity<>(orderHandler.cancelOrder(order).getMessage(), HttpStatus.ACCEPTED);
    }

}
