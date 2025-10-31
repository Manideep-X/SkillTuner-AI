package com.manideep.skilltunerai.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.SignupRequestDTO;
import com.manideep.skilltunerai.dto.UpdateUserRequestDTO;
import com.manideep.skilltunerai.entity.Users;

@Component
public class AuthMapper {

    private final PasswordEncoder passwordEncoder;

    public AuthMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Users signupReqToUsersObj(SignupRequestDTO signupRequestDTO) {

        Users users = new Users();

        users.setFirstName(signupRequestDTO.getFirstName());
        users.setLastName(signupRequestDTO.getLastName());
        users.setEmail(signupRequestDTO.getEmail());
        users.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));

        return users;

    }

    public SigninResponseDTO usersObjToSigninRes(Users users, String jwtToken) {

        SigninResponseDTO signinResponseDTO = new SigninResponseDTO();

        signinResponseDTO.setId(users.getId());
        signinResponseDTO.setFirstName(users.getFirstName());
        signinResponseDTO.setLastName(users.getLastName());
        signinResponseDTO.setEmail(users.getEmail());
        signinResponseDTO.setCreationTime(users.getCreationTime());
        signinResponseDTO.setUpdationTime(users.getUpdationTime());
        signinResponseDTO.setJwtToken(jwtToken);

        return signinResponseDTO;

    }

    public Users updateUserToUsersObj(UpdateUserRequestDTO updateUserRequestDTO, Users users) {

        users.setFirstName(updateUserRequestDTO.getFirstName());
        users.setLastName(updateUserRequestDTO.getLastName());

        return users;

    }

}
