package com.foodcourt.proyect.infrastructure.controller;

import com.foodcourt.proyect.infrastructure.dto.UserDTO;
import com.foodcourt.proyect.infrastructure.handler.UserHandler;
import com.foodcourt.proyect.infrastructure.security.AuthResponse;
import com.foodcourt.proyect.infrastructure.security.AuthService;
import com.foodcourt.proyect.infrastructure.security.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserHandler userHandler;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createOwner")
    @Qualifier("createOwner")
    public ResponseEntity<UserDTO> createOwner(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userHandler.createOwner(userDTO), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/createEmployee")
    @Qualifier("createEmployee")
    public ResponseEntity<UserDTO> createEmployee(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userHandler.createEmployee(userDTO), HttpStatus.ACCEPTED);
    }

    @PostMapping("/createClient")
    @Qualifier("createClient")
    public ResponseEntity<UserDTO> createClient(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userHandler.createClient(userDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Successfully logged out";
    }

}
