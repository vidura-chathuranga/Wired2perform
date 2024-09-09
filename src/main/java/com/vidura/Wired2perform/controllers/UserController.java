package com.vidura.Wired2perform.controllers;

import com.vidura.Wired2perform.dto.UserDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.models.UserEntity;
import com.vidura.Wired2perform.services.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

//    get all users
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
//    update user
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<UserDto>> updateUser(@PathVariable UUID id, @RequestBody UserEntity user){
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

//    get specific user Information
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

//    delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<UserDto>> deleteUser(@PathVariable UUID id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
