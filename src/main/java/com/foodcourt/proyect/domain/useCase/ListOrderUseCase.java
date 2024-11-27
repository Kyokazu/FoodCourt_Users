package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.OrderPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.servicePort.OrderServicePort;
import com.foodcourt.proyect.infrastructure.dto.ClientNotificationDTO;
import com.foodcourt.proyect.infrastructure.dto.DeliverOrderDTO;
import com.foodcourt.proyect.infrastructure.dto.NotificationMessageDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderDTO;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListOrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public List<OrderDTO> listOrders(Long size, String status) {
        Long owner = getRestaurantId();
        List<OrderDTO> orders = orderPersistencePort.findAll()
                .stream()
                .filter(order -> order.getStatus().toString().equals(status) && order.getRestaurantId().equals(owner))
                .limit(size)
                .map(o -> new OrderDTO(o.getId(), o.getRestaurantId(),
                        o.getClientId(), o.getPlateList(), o.getPlateQuantity(),
                        o.getStatus(), o.getAssignedEmployee(), o.getSecurityPin()))
                .collect(Collectors.toList());

        return orders;
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


    private Long getRestaurantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userFound = (UserEntity) authentication.getPrincipal();
        String employeeId = userFound.getId().toString();
        List<Restaurant> rest = restaurantPersistencePort.findAll()
                .stream()
                .filter(restaurant -> restaurant.getEmployees() != null)
                .filter(restaurant -> restaurant.getEmployees().contains(employeeId))
                .collect(Collectors.toList());

        return rest.get(0).getId();
    }
}
