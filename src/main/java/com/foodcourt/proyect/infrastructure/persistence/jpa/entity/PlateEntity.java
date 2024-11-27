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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PlateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "No puede existir un nombre con nombre nulo")
    private String name;
    @NotNull(message = "No puede existir un precio nulo")
    private int price;
    @NotNull(message = "No puede existir una descripción nula")
    private String description;
    @NotNull(message = "No puede existir una imagen nula")
    private String urlImage;
    @NotNull(message = "No puede existir una categoría nula")
    private String category;
    @NotNull(message = "No puede existir propietario nulo")
    private Long restaurantId;
    @NotNull
    private boolean active;

}
