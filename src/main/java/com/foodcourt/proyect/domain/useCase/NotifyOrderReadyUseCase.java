package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.model.*;
import com.foodcourt.proyect.domain.repositoryPort.OrderPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.StatusChangePersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.UserPersistencePort;
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
import java.util.Random;


@RequiredArgsConstructor
public class NotifyOrderReadyUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final StatusChangePersistencePort statusChangePersistencePort;


    @Override
    public NotificationMessageDTO notifyOrderReady(ClientNotificationDTO clientNotificationDTO) {
        validateData(clientNotificationDTO.getOrderId());
        Order order = orderPersistencePort.findById(clientNotificationDTO.getOrderId());
        User client = userPersistencePort.findById(order.getClientId());
        order.setStatus(OrderStatus.READY);
        order.setSecurityPin(generateSecurityPin());
        orderPersistencePort.update(order);
        registerStatusChange(order);
        return new NotificationMessageDTO(client.getPhone(), createMessage(order));
    }

    @Override
    public NotificationMessageDTO deliverOrder(DeliverOrderDTO deliverOrderDTO) {
        return null;
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
            throw new OrdenNoEnPreparacionException();
        }
    }

    private String createMessage(Order order) {
        Long orderId = order.getId();

        return "Hi! " + userPersistencePort.findById(orderPersistencePort.findById(orderId).getClientId()).getName()
                + ", The Order #" + orderId +
                " from the Restaurant " + restaurantPersistencePort.findById(orderPersistencePort.findById(orderId).getRestaurantId()).getName()
                + " is ready, use the security PIN " + order.getSecurityPin() + " to claim, enjoy it!!!";


    }

    private boolean existentOrder(Long orderId) {
        return orderPersistencePort.findById(orderId) != null;
    }

    private boolean validateOrderFromRestaurantEmployee(Long orderId) {
        return restaurantPersistencePort.findById(orderPersistencePort.findById(orderId).getRestaurantId()).getEmployees().contains(getEmployeeId().toString());
    }

    private boolean validateOrderStatus(Long orderId) {
        return orderPersistencePort.findById(orderId).getStatus() == OrderStatus.ON_PREPARATION;
    }

    private Long getEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }

    private int generateSecurityPin() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
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

}
