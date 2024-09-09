package com.vidura.Wired2perform.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
    private String message;
    private String token;


    public AuthenticationResponse(String token,String message) {
        this.token = token;
        this.message = message;
    }

}
