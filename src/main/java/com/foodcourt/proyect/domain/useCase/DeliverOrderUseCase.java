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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class DeliverOrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final StatusChangePersistencePort statusChangePersistencePort;

    @Override
    public NotificationMessageDTO deliverOrder(DeliverOrderDTO deliverOrderDTO) {
        validateData(deliverOrderDTO.getOrderId());
        Order order = orderPersistencePort.findById(deliverOrderDTO.getOrderId());
        validateSecurityPin(order.getSecurityPin(), deliverOrderDTO.getSecurityPin());
        order.setStatus(OrderStatus.DELIVERED);
        orderPersistencePort.update(order);
        registerStatusChange(order);
        return new NotificationMessageDTO("", "The order #" + deliverOrderDTO.getOrderId() + " was delivered successfully");
    }

    @Override
    public NotificationMessageDTO cancelOrder(OrderDTO order) {
        return null;
    }

    private void validateData(Long orderId) {
        if (!existentOrder(orderId)) {
            throw new OrdenInexistenteException();
        }
        if (!validateOrderFromRestaurantEmployee(orderId)) {
            throw new OrderDeOtroRestauranteException();
        }
        if (!validateOrderStatus(orderId)) {
            throw new OrderNoListaException();
        }
    }

    private void validateSecurityPin(int orderPin, int requestPin) {
        if (orderPin != requestPin) {
            throw new PinInvalidoException();
        }
    }

    private boolean existentOrder(Long orderId) {
        return orderPersistencePort.findById(orderId) != null;
    }

    private boolean validateOrderFromRestaurantEmployee(Long orderId) {
        return restaurantPersistencePort.findById(orderPersistencePort.findById(orderId).getRestaurantId()).getEmployees().contains(getEmployeeId().toString());
    }

    private boolean validateOrderStatus(Long orderId) {
        return orderPersistencePort.findById(orderId).getStatus() == OrderStatus.READY;
    }

    private Long getEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }

    private void registerStatusChange(Order order) {
        StatusChange statusChange = new StatusChange();
        statusChange.setOrderId(order.getId());
        statusChange.setClientId(order.getClientId());
        statusChange.setStatus(order.getStatus().name());
        statusChange.setChangeDate(new Date());
        statusChangePersistencePort.registerStatusChange(statusChange);
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public List<OrderDTO> listOrders(Long orders, String status) {
        return List.of();
    }

    @Override
    public Order assignOrder(Long employeeId) {
        return null;
    }

    @Override
    public NotificationMessageDTO notifyOrderReady(ClientNotificationDTO clientNotificationDTO) {
        return null;
    }


}
