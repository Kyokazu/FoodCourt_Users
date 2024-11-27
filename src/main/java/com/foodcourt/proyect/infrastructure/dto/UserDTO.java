package com.foodcourt.proyect.infrastructure.dto;

import com.foodcourt.proyect.domain.model.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String lastname;
    private String identification;
    private LocalDate birthday;
    private String phone;
    private String mail;
    private String password;
    private Role role;
}
