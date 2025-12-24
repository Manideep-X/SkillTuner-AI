package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
public class UserDetailsController {

    private final AuthService authService;

    public UserDetailsController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/checkpoint")
    public ResponseEntity<Void> checkingPoint() {
        return ResponseEntity.ok().build();
    }
    

    @GetMapping("/details")
    public ResponseEntity<SigninResponseDTO> getUserDetails() {
        return ResponseEntity.ok(authService.currentlyLoggedinUserDTO());
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signoutUser(HttpServletResponse httpServletResponse) {
        
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setAttribute("SameSite", "None");
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        httpServletResponse.addCookie(jwtCookie);

        return ResponseEntity.noContent().build();
        
    }

}
