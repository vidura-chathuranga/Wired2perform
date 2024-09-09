package com.vidura.Wired2perform.controllers;

import com.vidura.Wired2perform.models.AuthenticationResponse;
import com.vidura.Wired2perform.models.UserEntity;
import com.vidura.Wired2perform.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>  register (@RequestBody UserEntity user){
        return ResponseEntity.ok(authenticationService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>  login (@RequestBody UserEntity user){
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }

}
