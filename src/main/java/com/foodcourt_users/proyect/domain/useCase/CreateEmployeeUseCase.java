package com.foodcourt_users.proyect.domain.useCase;


import com.foodcourt_users.proyect.domain.exception.CelularNoValidoException;
import com.foodcourt_users.proyect.domain.exception.CorreoExistenteException;
import com.foodcourt_users.proyect.domain.exception.CorreoNoValidoException;
import com.foodcourt_users.proyect.domain.exception.MenorDeEdadException;
import com.foodcourt_users.proyect.domain.model.Role;
import com.foodcourt_users.proyect.domain.model.User;
import com.foodcourt_users.proyect.domain.repositoryPort.UserPersistencePort;
import com.foodcourt_users.proyect.domain.servicePort.UserServicePort;
import com.foodcourt_users.proyect.infrastructure.ApiClient.RestaurantApiClient;
import com.foodcourt_users.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Qualifier("createEmployee")
public class CreateEmployeeUseCase implements UserServicePort {


    private final UserPersistencePort userPersistencePort;
    private final RestaurantApiClient restaurantApiClient;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User createOwner(User user) {
        return null;
    }

    @Override
    public User createEmployee(User user) {
        validateUser(user);
        user.setRole(Role.EMPLOYEE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userPersistencePort.save(user);
        restaurantApiClient.updateEmployeeOnRestaurant(getOwnerId());
        return savedUser;
    }

    @Override
    public User createClient(User user) {
        return null;
    }

    private void validateUser(User usuario) {
        if (!correoValido(usuario.getMail())) {
            throw new CorreoNoValidoException();
        }

        if (!celularValido(usuario.getPhone())) {
            throw new CelularNoValidoException();
        }

        if (!edadValido(usuario.getBirthday())) {
            throw new MenorDeEdadException();
        }
        if (existentMail(usuario.getMail())) {
            throw new CorreoExistenteException();
        }
    }

    private Long getOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userFound = (UserEntity) authentication.getPrincipal();
        return userFound.getId();
    }


    private boolean correoValido(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    private boolean celularValido(String phone) {
        String phoneRegex = "^\\+?[0-9]+$";
        Pattern pat = Pattern.compile(phoneRegex);
        return pat.matcher(phone).matches();
    }

    private boolean edadValido(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    private boolean existentMail(String mail) {
        try {
            userPersistencePort.findIdByMail(mail);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
