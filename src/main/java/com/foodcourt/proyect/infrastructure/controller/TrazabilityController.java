package com.foodcourt.proyect.infrastructure.controller;

import com.foodcourt.proyect.infrastructure.dto.EmployeeRankingDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderRankingDTO;
import com.foodcourt.proyect.infrastructure.dto.StatusChangeDTO;
import com.foodcourt.proyect.infrastructure.handler.TrazabilityHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trazability")
public class TrazabilityController {

    private final TrazabilityHandler trazabilityHandler;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/getOrdersInfo")
    public ResponseEntity<List<StatusChangeDTO>> getOrdersInfo() {
        return new ResponseEntity<List<StatusChangeDTO>>(trazabilityHandler.getOrdersInfo(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/orderRanking")
    public ResponseEntity<List<OrderRankingDTO>> getOrderRanking() {
        return new ResponseEntity<List<OrderRankingDTO>>(trazabilityHandler.getOrderRanking(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/employeeRanking")
    public ResponseEntity<List<EmployeeRankingDTO>> getEmployeeRanking() {
        return new ResponseEntity<List<EmployeeRankingDTO>>(trazabilityHandler.getEmployeeRanking(), HttpStatus.ACCEPTED);
    }
}
