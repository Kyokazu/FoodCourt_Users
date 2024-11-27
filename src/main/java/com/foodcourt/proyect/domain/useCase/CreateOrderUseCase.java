package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.*;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Qualifier("createOrder")
public class CreateOrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final PlatePersistencePort platePersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final StatusChangePersistencePort statusChangePersistencePort;


    public Order createOrder(Order order) {
        order.setClientId(getClientId());
        order.setRestaurantId(getRestaurantId(order.getPlateList()));
        validateOrder(order);
        order.setStatus(OrderStatus.PENDING);
        order.setAssignedEmployee(null);
        Order newOrder = orderPersistencePort.save(order);
        registerStatusChange(newOrder);
        return newOrder;
    }

    @Override
    public List<OrderDTO> listOrders(Long orders, String status) {
        return null;
    }

    @Override
    public Order assignOrder(Long employeeId) {
        return null;
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

    private void registerStatusChange(Order order) {
        StatusChange statusChange = new StatusChange();
        statusChange.setOrderId(order.getId());
        statusChange.setClientId(order.getClientId());
        statusChange.setStatus(order.getStatus().name());
        statusChange.setChangeDate(new Date());
        statusChangePersistencePort.registerStatusChange(statusChange);
    }

    private void validateOrder(Order order) {

        if (!validateExistentRestaurant(order.getRestaurantId())) {
            throw new RestauranteInexistenteException();
        }
        if (!validateExistentPlate(order.getPlateList())) {
            throw new PlatoInexistenteException();
        }
        if (validateUserOrderStatus(order.getClientId())) {
            throw new PedidoDeClientInvalidoException();
        }
        if (!validatePlateSameRestaurant(order.getPlateList(), getRestaurantId(order.getPlateList()))) {
            throw new PedidoDeClientInvalidoException();
        }
    }

    private Long getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return userPersistencePort.findIdByMail(user.getMail());
    }

    private Long getRestaurantId(String plateIds) {
        List<String> list = Arrays.asList(plateIds.split(","));
        return platePersistencePort.findById(Long.parseLong(list.get(0))).getRestaurantId();
    }


    private boolean validateExistentRestaurant(Long restaurantId) {
        return restaurantPersistencePort.findById(restaurantId) != null;
    }

    private boolean validateExistentPlate(String plateIds) {
        List<String> list = Arrays.asList(plateIds.split(","));
        List<Plate> pltList = platePersistencePort.findAll();
        return list
                .stream()
                .allMatch(plateIdStr -> {
                    Long plateId = Long.parseLong(plateIdStr.trim());
                    return pltList.stream().anyMatch(plate -> plate.getId().equals(plateId));
                });
    }

    private boolean validatePlateSameRestaurant(String plateIds, Long restaurantId) {
        List<String> idList = Arrays.asList(plateIds.split(","));
        return idList
                .stream()
                .allMatch(plateIdStr -> {
                    Long plateId = Long.parseLong(plateIdStr.trim());
                    Plate existingPlate = platePersistencePort.findById(plateId);
                    return existingPlate != null && existingPlate.getRestaurantId().equals(restaurantId);
                });
    }

    private boolean validateUserOrderStatus(Long clientId) {
        List<Order> orders = orderPersistencePort.findAll();
        return orders
                .stream()
                .filter(order -> order.getClientId().equals(clientId))
                .anyMatch(order -> order.getStatus() == OrderStatus.PENDING
                        || order.getStatus() == OrderStatus.ON_PREPARATION
                        || order.getStatus() == OrderStatus.READY);
    }
}
