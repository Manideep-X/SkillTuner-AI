package com.manideep.skilltunerai.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdatePasswordDTO {

    @NotBlank(message = "Existing password is typed incorrectly!")
    String existingPassword;
    
    @NotBlank(message = "New password can't be empty!")
    String newPassword;

    @NotBlank(message = "It didn't match the new password!")
    String repeatNewPassword;
    
    public UpdatePasswordDTO() {
    }
    public UpdatePasswordDTO(String existingPassword, String newPassword, String repeatNewPassword) {
        this.existingPassword = existingPassword;
        this.newPassword = newPassword;
        this.repeatNewPassword = repeatNewPassword;
    }
    
    public String getExistingPassword() {
        return existingPassword;
    }
    public void setExistingPassword(String existingPassword) {
        this.existingPassword = existingPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }
    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }

}
