package com.foodcourt.proyect.domain.repositoryPort;

import com.foodcourt.proyect.domain.model.User;

public interface UserPersistencePort {
    User findById(Long aLong);

    User save(User entity);

    Long findIdByMail(String mail);
}
