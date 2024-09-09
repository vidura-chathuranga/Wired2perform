package com.vidura.Wired2perform.dto;

import com.vidura.Wired2perform.models.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String age;
}
