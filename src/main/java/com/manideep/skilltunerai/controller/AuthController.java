package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.SigninRequestDTO;
import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.SignupRequestDTO;
import com.manideep.skilltunerai.service.AuthService;
import com.manideep.skilltunerai.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    
    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signupUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        authService.signup(signupRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDTO> signinUser(@Valid @RequestBody SigninRequestDTO signinRequestDTO, HttpServletResponse httpServletResponse) {

        // Signing in the user and generating the token
        Map<String, Object> signinResponseDTO = authService.signin(signinRequestDTO);

        // Adding the token as a secure cookie to the response
        Cookie jwtCookie = new Cookie("jwt", (String) signinResponseDTO.get("jwtToken"));
        jwtCookie.setHttpOnly(true); // setting true means JS in browser can't access the token
        jwtCookie.setSecure(true); // setting true means cookie can only be sent over HTTPS not HTTP
        jwtCookie.setMaxAge(jwtUtil.getExpirationDurationInMilliSec()/1000);

        // Adding the cookie in the reponse
        httpServletResponse.addCookie(jwtCookie);

        return ResponseEntity.status(HttpStatus.OK).body((SigninResponseDTO) signinResponseDTO.get("user"));
    }

}
