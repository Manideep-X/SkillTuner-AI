package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/users")
public class UserDetailsController {

    private final AuthService authService;

    public UserDetailsController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/details")
    public ResponseEntity<SigninResponseDTO> getUserDetails() {
        return ResponseEntity.ok(authService.currentlyLoggedinUserDTO());
    }

}
