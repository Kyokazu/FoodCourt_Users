package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.OrderPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.StatusChangePersistencePort;
import com.foodcourt.proyect.domain.servicePort.OrderServicePort;
import com.foodcourt.proyect.infrastructure.dto.ClientNotificationDTO;
import com.foodcourt.proyect.infrastructure.dto.DeliverOrderDTO;
import com.foodcourt.proyect.infrastructure.dto.NotificationMessageDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderDTO;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Qualifier("assignOrder")
public class AssignOrder implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final StatusChangePersistencePort statusChangePersistencePort;


    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public List<OrderDTO> listOrders(Long orders, String status) {
        return List.of();
    }

    @Override
    public Order assignOrder(Long orderId) {
        validateExistentOrder(orderId);
        Order order = orderPersistencePort.findById(orderId);
        order.setAssignedEmployee(getEmployeeId());
        order.setStatus(OrderStatus.ON_PREPARATION);
        orderPersistencePort.update(order);
        registerStatusChange(order);
        return order;
    }

    @Override
    public NotificationMessageDTO notifyOrderReady(ClientNotificationDTO clientNotificationDTO) {
        return null;
    }

    @Override
    public NotificationMessageDTO deliverOrder(DeliverOrderDTO deliverOrderDTO) {
        return null;
    }

    @Override
    public NotificationMessageDTO cancelOrder(OrderDTO order) {
        return null;
    }

    private void validateExistentOrder(Long orderId) {
        Order order = orderPersistencePort.findById(orderId);
        if (order == null) {
            throw new OrdenInexistenteException();
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrdenNoPendienteException();
        }
        if (!validateOrderFromEmployeeRestaurant(order)) {
            throw new OrderDeOtroRestauranteException();
        }
    }

    private void registerStatusChange(Order order) {
        StatusChange statusChange = new StatusChange();
        statusChange.setOrderId(order.getId());
        statusChange.setClientId(order.getClientId());
        statusChange.setStatus(order.getStatus().name());
        statusChange.setChangeDate(new Date());
        statusChangePersistencePort.registerStatusChange(statusChange);
    }


    private boolean validateOrderFromEmployeeRestaurant(Order order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return restaurantPersistencePort.findById(order.getRestaurantId()).getEmployees().contains(user.getId().toString());
    }

    private Long getEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }

}
