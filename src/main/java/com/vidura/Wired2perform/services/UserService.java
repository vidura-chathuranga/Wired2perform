package com.vidura.Wired2perform.services;

import com.vidura.Wired2perform.dto.UserDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.models.UserEntity;
import com.vidura.Wired2perform.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

//    for testing
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return modelMapper.map(users,new TypeToken<List<UserDto>>(){}.getType());
    }

    public GenericResponse<UserDto> updateUser(UUID userId, UserEntity user){
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

//        update user fields
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setPhone(user.getPhone());
        existingUser.setAge(user.getAge());

//        save user
       userRepository.save(existingUser);


        return new GenericResponse<>("user updated successfully", modelMapper.map(existingUser, UserDto.class));
    }

    public UserDto getUserById(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        return modelMapper.map(user, UserDto.class);
    }

//    delete user
    public GenericResponse<UserDto> deleteUser(UUID userId) {
        Map<String,String> response = new HashMap<>();

//        check whether user is existing or not
        userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

//        delete user
        userRepository.deleteById(userId);

        return new GenericResponse<>("user deleted successfully", null);
    }
}
