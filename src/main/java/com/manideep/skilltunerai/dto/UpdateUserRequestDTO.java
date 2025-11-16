package com.manideep.skilltunerai.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequestDTO {

    @NotBlank(message = "First name can't be empty!")
    String firstName;
    
    @NotBlank(message = "Last name can't be empty!")
    String lastName;
    
    public UpdateUserRequestDTO() {
    }
    public UpdateUserRequestDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
