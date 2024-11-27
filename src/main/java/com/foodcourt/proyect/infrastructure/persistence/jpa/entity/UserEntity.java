package com.foodcourt.proyect.infrastructure.persistence.jpa.entity;

import com.foodcourt.proyect.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "No puede existir un rol con nombre nulo")
    @Size(min = 2, max = 15, message = "El tamaño del nombre debe estar entre 2 y 15 caracteres")
    private String name;
    @NotNull(message = "No puede existir un apellido nulo")
    @Size(min = 2, max = 16, message = "El tamaño del apellido debe ser entre 2 y 15 caracteres")
    private String lastname;
    @NotNull
    @Size(min = 5, max = 15, message = "La idenfiticación debe ser entre 2 y 15 caracteres")
    private String identification;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private String phone;
    @NotNull
    @Size(min = 10, max = 30, message = "El correo debe ser entre 10 y 30 caracteres")
    @Column(unique = true)
    private String mail;
    @NotNull
    @Size(min = 5, max = 15, message = "La contraseña debe ser entre 5 y 15 caracteres")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
