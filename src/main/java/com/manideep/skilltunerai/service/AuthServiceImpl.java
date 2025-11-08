package com.manideep.skilltunerai.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manideep.skilltunerai.dto.SigninRequestDTO;
import com.manideep.skilltunerai.dto.SigninResponseDTO;
import com.manideep.skilltunerai.dto.SignupRequestDTO;
import com.manideep.skilltunerai.dto.UpdatePasswordDTO;
import com.manideep.skilltunerai.dto.UpdateUserRequestDTO;
import com.manideep.skilltunerai.entity.Users;
import com.manideep.skilltunerai.exception.DuplicateValueException;
import com.manideep.skilltunerai.mapper.AuthMapper;
import com.manideep.skilltunerai.repository.UsersRepository;
import com.manideep.skilltunerai.util.JwtUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UsersRepository usersRepository, AuthMapper authMapper,
            JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.authMapper = authMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signup(SignupRequestDTO signupRequestDTO) throws DuplicateValueException, PersistenceException {
        
        // Throws error if user exists.
        if (usersRepository.findByEmail(signupRequestDTO.getEmail()).isPresent())
            throw new DuplicateValueException("This email already exists! Try signing in");

        Users newUser = authMapper.signupReqToUsersObj(signupRequestDTO);
        try {
            usersRepository.save(newUser);
        } catch (Exception e) {
            // Throws error if the data couldn't be saved in the DB. 
            throw new PersistenceException("Unknown error occured while saving data to the database!");
        }

    }

    @Override
    public SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) throws BadCredentialsException {
        
        try {
            // Authenticate the user by passing an unauthenticated token UsernamePasswordAuthenticationToken, which contains all credentials.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequestDTO.getEmail(), signinRequestDTO.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email and/or password! Try again");
        }

        // Generates token 
        String jwtToken = jwtUtil.generateToken(signinRequestDTO.getEmail());
        
        Users users = usersRepository.findByEmail(signinRequestDTO.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Invalid email and/or password! Try again"));
        // Returns the signin response which contains the JWT token
        return authMapper.usersObjToSigninRes(users, jwtToken);
        
    }

    @Override
    public Users currentlyLoggedinUser() {
        
        // SecurityContextHolder has the currently logged-in user details as mentioned in the JwtRequestFilter
        // getAuthentication returns all authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usersEntityByItsEmail(authentication.getName());
        
    }

    @Override
    public SigninResponseDTO currentlyLoggedinUserDTO() {

        return authMapper.usersObjToSigninRes(currentlyLoggedinUser(), null);

    }

    @Override
    public Users usersEntityByItsEmail(String email) throws EntityNotFoundException {
        
        return usersRepository.findByEmail(email)
            .orElseThrow(
                () -> new EntityNotFoundException("User not found with email: " + email));
        
    }

    @Override
    public void updateCurrentUser(UpdateUserRequestDTO updateUserRequestDTO) {
        
        // Maps the update user DTO to users entity object
        Users updatedUser = authMapper.updateUserToUsersObj(updateUserRequestDTO, currentlyLoggedinUser());

        // Save the updated user object to the database
        usersRepository.save(updatedUser);
        
    }

    @Override
    public void updatePasswordOfCurrUser(UpdatePasswordDTO updatePasswordDTO) {
        
        Users currUser = currentlyLoggedinUser();

        if (!passwordEncoder.matches(updatePasswordDTO.getExistingPassword(), currUser.getPassword())) {
            throw new IllegalArgumentException("Existing password you typed is incorrect!");
        }

        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getRepeatNewPassword())) {
            throw new IllegalArgumentException("New password didn't matched with the repeated one!");
        }

        currUser.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        usersRepository.save(currUser);
        
    }

}
