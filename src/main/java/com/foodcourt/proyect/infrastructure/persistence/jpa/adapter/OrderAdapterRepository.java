package com.foodcourt.proyect.infrastructure.persistence.jpa.adapter;

import com.foodcourt.proyect.domain.repositoryPort.OrderPersistencePort;
import com.foodcourt.proyect.infrastructure.mapper.OrderEntityMapper;
import com.foodcourt.proyect.infrastructure.persistence.jpa.repository.OrderRepository;
import com.foodcourt.proyect.infrastructure.persistence.mongodb.repository.StatusChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderAdapterRepository implements OrderPersistencePort {

    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final StatusChangeRepository statusChangeRepository;

    @Override
    public Order findById(Long aLong) {
        return orderEntityMapper.AToB(orderRepository.findById(aLong).get());
    }

    @Override
    public List<Order> findAll() {
        return orderEntityMapper.AToB(orderRepository.findAll().stream()).collect(Collectors.toList());
    }

    @Override
    public Order save(Order entity) {
        return orderEntityMapper.AToB(orderRepository.save(orderEntityMapper.BToA(entity)));
    }

    @Override
    public void update(Order entity) {
        orderRepository.save(orderEntityMapper.BToA(entity));
    }

    public void delete(Order entity) {
        orderRepository.delete(orderEntityMapper.BToA(entity));
    }

}
