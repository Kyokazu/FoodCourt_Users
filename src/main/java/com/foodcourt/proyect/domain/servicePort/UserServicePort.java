package com.foodcourt.proyect.domain.servicePort;

import com.foodcourt.proyect.domain.model.User;

public interface UserServicePort {

    public User createOwner(User user);

    public User createEmployee(User user);

    public User createClient(User user);
}
