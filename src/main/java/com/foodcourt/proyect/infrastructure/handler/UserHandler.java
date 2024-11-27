package com.foodcourt.proyect.infrastructure.handler;

import com.foodcourt.proyect.domain.servicePort.UserServicePort;
import com.foodcourt.proyect.infrastructure.dto.UserDTO;
import com.foodcourt.proyect.infrastructure.mapper.UserDTOMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service


public class UserHandler {

    private final UserDTOMapper userDTOMapper;
    private final UserServicePort createOwnerServicePort;
    private final UserServicePort createEmployeeServicePort;
    private final UserServicePort createClientServicePort;


    public UserHandler(
            @Qualifier("createEmployee") UserServicePort createEmployeeServicePort,
            UserDTOMapper userDTOMapper,
            @Qualifier("createOwner") UserServicePort createOwnerServicePort,
            @Qualifier("createClient") UserServicePort createClientServicePort) {
        this.createEmployeeServicePort = createEmployeeServicePort;
        this.userDTOMapper = userDTOMapper;
        this.createOwnerServicePort = createOwnerServicePort;
        this.createClientServicePort = createClientServicePort;
    }

    public UserDTO createOwner(UserDTO userDTO) {
        return userDTOMapper.BToA(createOwnerServicePort.createOwner(userDTOMapper.AToB(userDTO)));
    }

    public UserDTO createEmployee(UserDTO userDTO) {
        return userDTOMapper.BToA(createEmployeeServicePort.createEmployee(userDTOMapper.AToB(userDTO)));
    }

    public UserDTO createClient(UserDTO userDTO) {
        return userDTOMapper.BToA(createClientServicePort.createClient(userDTOMapper.AToB(userDTO)));
    }
}
