package com.foodcourt.proyect.infrastructure.persistence.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.Date;

@Document(collection = "statusChange")
@Data
public class StatusChangeEntity {
    @Id
    private String id;
    private Long clientId;
    private Long orderId;
    private String status;
    private Date changeDate;
}
