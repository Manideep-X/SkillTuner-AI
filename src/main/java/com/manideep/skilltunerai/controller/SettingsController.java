package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.UpdatePasswordDTO;
import com.manideep.skilltunerai.dto.UpdateUserRequestDTO;
import com.manideep.skilltunerai.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final AuthService authService;

    public SettingsController(AuthService authService) {
        this.authService = authService;
    }

    @PutMapping("/username")
    public ResponseEntity<SigninResponseDTO> renameUsername(@RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        
        return ResponseEntity.ok(authService.updateCurrentUser(updateUserRequestDTO));
        
    }
    
    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        
        authService.updatePasswordOfCurrUser(updatePasswordDTO);
        return ResponseEntity.noContent().build();

    }
    

}
