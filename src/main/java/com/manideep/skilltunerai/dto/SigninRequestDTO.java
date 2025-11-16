package com.manideep.skilltunerai.dto;

import jakarta.validation.constraints.NotBlank;

public class SigninRequestDTO {

    @NotBlank(message = "Email address is required!")
    private String email;
    
    @NotBlank(message = "Password is required!")
    private String password;
    
    public SigninRequestDTO() {
    }
    public SigninRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
