package com.manideep.skilltunerai.dto;

import java.time.LocalDateTime;

public class SigninResponseDTO {

    private long id;
    private String lastName;
    private String firstName;
    private String email;
    private LocalDateTime creationTime;
    private LocalDateTime updationTime;
    private String jwtToken;
    
    public SigninResponseDTO() {
    }
    public SigninResponseDTO(long id, String lastName, String firstName, String email,
            LocalDateTime creationTime, LocalDateTime updationTime, String jwtToken) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
        this.jwtToken = jwtToken;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
    
    public LocalDateTime getUpdationTime() {
        return updationTime;
    }
    public void setUpdationTime(LocalDateTime updationTime) {
        this.updationTime = updationTime;
    }
    
    public String getJwtToken() {
        return jwtToken;
    }
    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
