package com.manideep.skilltunerai.dto;

public class UpdateUserRequestDTO {

    String firstName;
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
