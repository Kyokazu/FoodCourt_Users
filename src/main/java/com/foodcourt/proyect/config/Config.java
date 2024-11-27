package com.foodcourt.proyect.config;

import com.foodcourt.proyect.domain.repositoryPort.*;
import com.foodcourt.proyect.domain.servicePort.*;
import com.foodcourt.proyect.domain.useCase.*;
import com.foodcourt.proyect.infrastructure.persistence.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final UserRepository userRepository;

    @Bean
    @Qualifier("createOwner")
    public UserServicePort createOwner(UserPersistencePort userRepository) {
        return new CreateOwnerUseCase(userRepository);
    }

    @Bean
    @Qualifier("createEmployee")
    public UserServicePort createEmployee(UserPersistencePort userRepository, RestaurantPersistencePort restaurantPersistencePort) {
        return new CreateEmployeeUseCase(userRepository, restaurantPersistencePort);
    }

    @Bean
    @Qualifier("createClient")
    public UserServicePort createClient(UserPersistencePort userRepository) {
        return new CreateClientUseCase(userRepository);
    }

    @Bean
    @Qualifier("listRestaurant")
    public RestaurantServicePort restaurantServicePort(RestaurantPersistencePort restaurantPersistence) {
        return new ListRestaurantUseCase(restaurantPersistence);
    }

    @Qualifier("createRestaurant")
    @Bean
    public RestaurantServicePort listRestaurantUseCase(RestaurantPersistencePort restaurantPersistence, UserPersistencePort userRepository) {
        return new CreateRestaurantUseCase(restaurantPersistence, userRepository);
    }

    @Bean
    @Qualifier("createPlate")
    public PlateServicePort createPlateUseCase(PlatePersistencePort platePersistencePort, RestaurantPersistencePort restaurantPersistencePort) {
        return new CreatePlateUseCase(platePersistencePort, restaurantPersistencePort);

    }

    @Bean
    @Qualifier("updatePlate")
    public PlateServicePort updatePlateUseCase(PlatePersistencePort platePersistencePort, RestaurantPersistencePort restaurantPersistencePort, UserPersistencePort userRepository) {
        return new UpdatePlateUseCase(platePersistencePort, restaurantPersistencePort, userRepository);

    }

    @Bean
    @Qualifier("listPlate")
    public PlateServicePort listPlateUseCase(PlatePersistencePort platePersistencePort, RestaurantPersistencePort restaurantPersistencePort) {
        return new ListPlateUseCase(platePersistencePort, restaurantPersistencePort);
    }

    @Bean
    @Qualifier("enableUnablePlate")
    public PlateServicePort ableEnablePlate(
            PlatePersistencePort platePersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            UserPersistencePort userRepository) {
        return new EnableUnablePlateUseCase(platePersistencePort, restaurantPersistencePort, userRepository);
    }

    @Bean
    @Qualifier("createOrder")
    public OrderServicePort createOrder(
            OrderPersistencePort orderPersistencePort,
            PlatePersistencePort platePersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            UserPersistencePort userRepository,
            StatusChangePersistencePort statusChangePersistencePort) {
        return new CreateOrderUseCase(orderPersistencePort, platePersistencePort, restaurantPersistencePort, userRepository, statusChangePersistencePort);
    }

    @Bean
    @Qualifier("listOrders")
    public OrderServicePort listOrders(
            OrderPersistencePort orderPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort) {
        return new ListOrderUseCase(orderPersistencePort, restaurantPersistencePort);
    }

    @Bean
    @Qualifier("assignOrder")
    public OrderServicePort assignOrder(
            OrderPersistencePort orderPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            StatusChangePersistencePort statusChangePersistencePort) {
        return new AssignOrder(orderPersistencePort, restaurantPersistencePort, statusChangePersistencePort);
    }

    @Bean
    @Qualifier("notifyReadyOrder")
    public OrderServicePort notifyReadyOrder(
            OrderPersistencePort orderPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            UserPersistencePort userRepository,
            StatusChangePersistencePort statusChangePersistencePort) {
        return new NotifyOrderReadyUseCase(orderPersistencePort, restaurantPersistencePort, userRepository, statusChangePersistencePort);
    }

    @Bean
    @Qualifier("deliverOrder")
    public OrderServicePort deliverOrder(
            OrderPersistencePort orderPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            StatusChangePersistencePort statusChangePersistencePort) {
        return new DeliverOrderUseCase(orderPersistencePort, restaurantPersistencePort, statusChangePersistencePort);
    }

    @Bean
    @Qualifier("cancelOrder")
    public OrderServicePort cancelOrder(
            OrderPersistencePort orderPersistencePort,
            StatusChangePersistencePort statusChangePersistencePort) {
        return new CancelOrderUseCase(orderPersistencePort, statusChangePersistencePort);
    }

    @Bean
    @Qualifier("statusChange")
    public StatusChangeServicePort statusChange(
            StatusChangePersistencePort statusChangePersistencePort) {
        return new RegisterStatusChangeUseCase(statusChangePersistencePort);
    }

    @Bean
    @Qualifier("orderEfficiency")
    public StatusChangeServicePort orderEfficiency(
            StatusChangePersistencePort statusChangePersistencePort,
            OrderPersistencePort orderPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort) {
        return new OrderEfficiencyUseCase(statusChangePersistencePort, orderPersistencePort, restaurantPersistencePort);
    }


    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public UserDetailsService userDetailService() {
        return mail -> userRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException("Mail not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
