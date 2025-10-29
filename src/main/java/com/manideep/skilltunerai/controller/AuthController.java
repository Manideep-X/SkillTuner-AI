package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.SigninRequestDTO;
import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.SignupRequestDTO;
import com.manideep.skilltunerai.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signupUser(@RequestBody SignupRequestDTO signupRequestDTO) {
        authService.signup(signupRequestDTO);
        return ResponseEntity.ok(null);
    }
    
    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDTO> signinUser(@RequestBody SigninRequestDTO signinRequestDTO) {
        SigninResponseDTO signinResponseDTO = authService.signin(signinRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(signinResponseDTO);
    }

}
