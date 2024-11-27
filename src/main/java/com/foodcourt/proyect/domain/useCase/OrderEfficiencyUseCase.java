package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.OrderPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.RestaurantPersistencePort;
import com.foodcourt.proyect.domain.repositoryPort.StatusChangePersistencePort;
import com.foodcourt.proyect.domain.servicePort.StatusChangeServicePort;
import com.foodcourt.proyect.infrastructure.dto.EmployeeRankingDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderRankingDTO;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderEfficiencyUseCase implements StatusChangeServicePort {

    private final StatusChangePersistencePort statusChangePersistencePort;
    private final OrderPersistencePort orderPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;


    @Override
    public List<StatusChange> checkOrderChangelog() {
        return List.of();
    }

    @Override
    public List<OrderRankingDTO> getOrderRanking() {
        Long restaurantId = restaurantPersistencePort.findRestaurantByOwnerId(getOwnerId()).getId();
        List<StatusChange> statusList = statusChangePersistencePort.getAll();
        List<StatusChange> filteredCreated = statusList
                .stream()
                .filter(statusChange -> (statusChange.getStatus().equals("PENDING")
                        || statusChange.getStatus().equals("DELIVERED"))
                        && (orderPersistencePort.findById(statusChange.getOrderId()).getRestaurantId().equals(restaurantId)))
                .collect(Collectors.toList());

        Map<Long, List<StatusChange>> groupedByOrderId = filteredCreated
                .stream()
                .collect(Collectors.groupingBy(StatusChange::getOrderId));

        List<OrderRankingDTO> orderRankingList = new ArrayList<>();

        for (Map.Entry<Long, List<StatusChange>> entry : groupedByOrderId.entrySet()) {
            Long orderId = entry.getKey();
            List<StatusChange> changes = entry.getValue();

            StatusChange created = changes.stream()
                    .filter(change -> change.getStatus().equals("PENDING"))
                    .min(Comparator.comparing(StatusChange::getChangeDate))
                    .orElse(null);

            StatusChange delivered = changes.stream()
                    .filter(change -> change.getStatus().equals("DELIVERED"))
                    .max(Comparator.comparing(StatusChange::getChangeDate))
                    .orElse(null);

            if (created != null && delivered != null) {
                Duration duration = Duration.between(created.getChangeDate().toInstant(), delivered.getChangeDate().toInstant());
                long minutes = duration.toMinutes();

                OrderRankingDTO orderRank = new OrderRankingDTO(orderId, minutes);
                orderRankingList.add(orderRank);
            }
        }

        return orderRankingList;
    }

    @Override
    public List<EmployeeRankingDTO> getEmployeeRanking() {
        // Obtener todos los pedidos asignados
        List<Order> orders = orderPersistencePort.findAll()
                .stream()
                .filter(o -> o.getAssignedEmployee() != null)
                .collect(Collectors.toList());

        // Obtener IDs de empleados del restaurante del dueño actual
        String[] employeesId = restaurantPersistencePort.findRestaurantByOwnerId(getOwnerId()).getEmployees().split(",");

        // Obtener tiempos de los pedidos
        List<OrderRankingDTO> ordersTime = getOrderRanking();

        // Mapa para acumular tiempos totales por empleado
        Map<Long, Long> employeeTimeMap = new HashMap<>();

        // Iterar sobre los IDs de los empleados
        for (String employeeId : employeesId) {
            // Filtrar pedidos asignados al empleado actual
            List<Long> employeeOrders = orders.stream()
                    .filter(o -> o.getAssignedEmployee().equals(Long.parseLong(employeeId)))
                    .map(Order::getId)
                    .collect(Collectors.toList());

            // Calcular tiempo total basado en los tiempos de los pedidos
            long totalTime = ordersTime.stream()
                    .filter(orderTime -> employeeOrders.contains(orderTime.getOrderId()))
                    .mapToLong(OrderRankingDTO::getDeliverTime)
                    .sum();

            // Guardar en el mapa
            employeeTimeMap.put(Long.parseLong(employeeId), totalTime);
        }

        // Convertir el mapa a una lista de EmployeeRankingDTO
        return employeeTimeMap.entrySet()
                .stream()
                .map(entry -> new EmployeeRankingDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(EmployeeRankingDTO::getOrderCompletionTime))
                .collect(Collectors.toList());
    }

    private Long getOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userFound = (UserEntity) authentication.getPrincipal();
        return userFound.getId();
    }

}
