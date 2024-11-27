package com.foodcourt.proyect.infrastructure.persistence.jpa.adapter;

import com.foodcourt.proyect.domain.model.User;
import com.foodcourt.proyect.domain.repositoryPort.UserPersistencePort;
import com.foodcourt.proyect.infrastructure.mapper.UserEntityMapper;
import com.foodcourt.proyect.infrastructure.persistence.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserAdapterRepository implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User findById(Long aLong) {
        return userEntityMapper.AToB(userRepository.findById(aLong).get());
    }

    public List<User> findAll() {
        return userEntityMapper.AToB(userRepository.findAll().stream()).collect(Collectors.toList());
    }

    @Override
    public User save(User entity) {
        return userEntityMapper.AToB(userRepository.save(userEntityMapper.BToA(entity)));
    }

    public void update(User entity) {
        userRepository.save(userEntityMapper.BToA(entity));
    }

    public void delete(User entity) {
        userRepository.delete(userEntityMapper.BToA(entity));
    }

    @Override
    public Long findIdByMail(String mail) {
        return userRepository.findByMail(mail).get().getId();
    }
}
