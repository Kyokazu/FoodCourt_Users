package com.foodcourt.proyect.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private String lastname;
    private String identification;
    private String phone;
    private LocalDate birthday;
    private String mail;
    private String password;
    private Role role;

}
