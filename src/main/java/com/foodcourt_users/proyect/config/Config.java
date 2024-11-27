package com.foodcourt_users.proyect.config;

import com.foodcourt_users.proyect.domain.repositoryPort.UserPersistencePort;
import com.foodcourt_users.proyect.domain.servicePort.UserServicePort;
import com.foodcourt_users.proyect.domain.useCase.CreateClientUseCase;
import com.foodcourt_users.proyect.domain.useCase.CreateEmployeeUseCase;
import com.foodcourt_users.proyect.domain.useCase.CreateOwnerUseCase;
import com.foodcourt_users.proyect.infrastructure.ApiClient.RestaurantApiClient;
import com.foodcourt_users.proyect.infrastructure.persistence.jpa.repository.UserRepository;
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
    public UserServicePort createEmployee(UserPersistencePort userRepository,
                                          RestaurantApiClient restaurantApiClient) {
        return new CreateEmployeeUseCase(userRepository, restaurantApiClient);
    }

    @Bean
    @Qualifier("createClient")
    public UserServicePort createClient(UserPersistencePort userRepository) {
        return new CreateClientUseCase(userRepository);
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
