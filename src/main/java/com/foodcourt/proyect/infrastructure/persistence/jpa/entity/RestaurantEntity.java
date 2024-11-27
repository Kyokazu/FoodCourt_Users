package com.foodcourt.proyect.infrastructure.persistence.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Name field must not be null")
    private String name;
    @NotNull(message = "NIT field must not be null")
    private String nit;
    @NotNull(message = "Address field must not be null")
    private String address;
    @NotNull (message = "Phone field must not be null")
    @Size(max = 13, message ="Phone field max size allowed is 13 characters")
    private String phone;
    @NotNull (message = "URL logo field must not be null")
    private String urlLogo;
    @NotNull(message = "OwnerId field must not be null")
    private Long ownerId;
    private String employees;

}
