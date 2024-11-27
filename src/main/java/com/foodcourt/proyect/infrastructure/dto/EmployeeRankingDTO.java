package com.foodcourt.proyect.infrastructure.dto;

import lombok.Data;

@Data
public class EmployeeRankingDTO {

    private Long employeeId;
    private Long orderCompletionTime;

    public EmployeeRankingDTO(Long employeeId, Long orderCompletionTime) {
        this.orderCompletionTime = orderCompletionTime;
        this.employeeId = employeeId;
    }
}
