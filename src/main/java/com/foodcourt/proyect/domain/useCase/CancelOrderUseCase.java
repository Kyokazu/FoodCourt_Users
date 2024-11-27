package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.OrderPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.StatusChangePersistencePort;
import com.foodcourt.proyect.domain.servicePort.OrderServicePort;
import com.foodcourt.proyect.infrastructure.dto.ClientNotificationDTO;
import com.foodcourt.proyect.infrastructure.dto.DeliverOrderDTO;
import com.foodcourt.proyect.infrastructure.dto.NotificationMessageDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderDTO;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class CancelOrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final StatusChangePersistencePort statusChangePersistencePort;

    @Override
    public NotificationMessageDTO cancelOrder(OrderDTO order) {
        NotificationMessageDTO notificationMessageDTO = new NotificationMessageDTO("", "");
        if (!validateData(order.getId())) {
            notificationMessageDTO.setMessage("We are sorry, your order #"
                    + order.getId()
                    + " is on preparation and can't be cancelled");
        } else {
            Order order1 = orderPersistencePort.findById(order.getId());
            order1.setStatus(OrderStatus.CANCELED);
            orderPersistencePort.update(order1);
            registerStatusChange(order1);
            String message = "Your order #"
                    + order.getId()
                    + " has been canceled!";
            notificationMessageDTO.setMessage(message);
        }
        return notificationMessageDTO;
    }


    private boolean validateData(Long orderId) {
        if (!existentOrder(orderId)) {
            throw new OrdenInexistenteException();
        }
        return validateOrderStatus(orderId);
    }


    private boolean existentOrder(Long orderId) {
        return orderPersistencePort.findById(orderId) != null;
    }

    private boolean validateOrderStatus(Long orderId) {
        return orderPersistencePort.findById(orderId).getStatus() == OrderStatus.PENDING;
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

    @Override
    public NotificationMessageDTO deliverOrder(DeliverOrderDTO deliverOrderDTO) {
        return null;
    }

}
