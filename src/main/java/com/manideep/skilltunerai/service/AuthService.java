package com.manideep.skilltunerai.service;

import org.springframework.security.authentication.BadCredentialsException;

import com.manideep.skilltunerai.dto.SigninRequestDTO;
import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.SignupRequestDTO;
import com.manideep.skilltunerai.dto.UpdatePasswordDTO;
import com.manideep.skilltunerai.dto.UpdateUserRequestDTO;
import com.manideep.skilltunerai.entity.Users;
import com.manideep.skilltunerai.exception.DuplicateValueException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

public interface AuthService {

    // Method for registering new user
    void signup(SignupRequestDTO signupRequestDTO) throws DuplicateValueException, PersistenceException;
    
    // Method for signing in exsisting user
    SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) throws BadCredentialsException;

    // Method for getting the user entity who is currently signed-in
    Users currentlyLoggedinUser();

    // Method for getting the user entity by its email address
    Users usersEntityByItsEmail(String email) throws EntityNotFoundException;

    // Method to update the name of currently logged in user
    void updateCurrentUser(UpdateUserRequestDTO updateUserRequestDTO);

    // Method to update the password of currently logged in user
    void updatePasswordOfCurrUser(UpdatePasswordDTO updatePasswordDTO);

}
