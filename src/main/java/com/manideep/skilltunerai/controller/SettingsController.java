package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> renameUsername(@RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        
        authService.updateCurrentUser(updateUserRequestDTO);
        return ResponseEntity.ok(null);
        
    }
    
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        
        authService.updatePasswordOfCurrUser(updatePasswordDTO);
        return ResponseEntity.ok(null);

    }
    

}
