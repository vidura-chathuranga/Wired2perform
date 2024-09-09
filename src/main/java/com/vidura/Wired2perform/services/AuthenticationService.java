package com.vidura.Wired2perform.services;

import com.vidura.Wired2perform.models.AuthenticationResponse;
import com.vidura.Wired2perform.models.UserEntity;
import com.vidura.Wired2perform.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserEntity request){

//        check user is existing or not
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email is already in use");
        }

        //save user
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAge(request.getAge());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token,"User registered successfully");
    }

    public AuthenticationResponse authenticate(UserEntity request) {

//        check whether user is existing
        UserEntity user = userRepository.findByEmail(request.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid username or password"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token  = jwtService.generateToken(user);

        return new AuthenticationResponse(token,"User logged in successfully");
    }

}
