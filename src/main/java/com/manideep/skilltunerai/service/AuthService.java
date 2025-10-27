package com.manideep.skilltunerai.service;

import com.manideep.skilltunerai.dto.SigninRequestDTO;
import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.SignupRequestDTO;

public interface AuthService {

    // Method for registering new user
    void signup(SignupRequestDTO signupRequestDTO);
    
    // Method for signing in exsisting user
    SigninResponseDTO signin(SigninRequestDTO signinRequestDTO);

}
