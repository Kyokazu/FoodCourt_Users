package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.exception.CelularNoValidoException;
import com.foodcourt.proyect.domain.exception.CorreoExistenteException;
import com.foodcourt.proyect.domain.exception.CorreoNoValidoException;
import com.foodcourt.proyect.domain.exception.MenorDeEdadException;
import com.foodcourt.proyect.domain.model.Role;
import com.foodcourt.proyect.domain.model.User;
import com.foodcourt.proyect.domain.repositoryPort.UserPersistencePort;
import com.foodcourt.proyect.domain.servicePort.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Qualifier("createOwner")
public class CreateOwnerUseCase implements UserServicePort {

    private final UserPersistencePort userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User createOwner(User user) {
        validateUser(user);
        user.setRole(Role.OWNER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public User createEmployee(User user) {
        return null;
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
            userRepository.findIdByMail(mail);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
