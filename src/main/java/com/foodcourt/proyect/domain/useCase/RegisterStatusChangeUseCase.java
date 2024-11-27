package com.foodcourt.proyect.domain.useCase;

import com.foodcourt.proyect.domain.repositoryPort.StatusChangePersistencePort;
import com.foodcourt.proyect.domain.servicePort.StatusChangeServicePort;
import com.foodcourt.proyect.infrastructure.dto.EmployeeRankingDTO;
import com.foodcourt.proyect.infrastructure.dto.OrderRankingDTO;
import com.foodcourt.proyect.infrastructure.persistence.jpa.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RegisterStatusChangeUseCase implements StatusChangeServicePort {

    private final StatusChangePersistencePort statusChangePersistencePort;

    @Override
    public List<StatusChange> checkOrderChangelog() {
        List<StatusChange> statusChangeList = statusChangePersistencePort.getAll();
        Long clientId = getClientId();
        return statusChangeList.stream()
                .filter(statusChange -> statusChange.getClientId().equals(clientId))
                .collect(Collectors.toList());

    }

    @Override
    public List<OrderRankingDTO> getOrderRanking() {
        return null;
    }

    @Override
    public List<EmployeeRankingDTO> getEmployeeRanking() {
        return null;
    }

    private Long getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return user.getId();
    }

}

