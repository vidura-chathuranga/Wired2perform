package com.vidura.Wired2perform.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoListItemDto {
    private UUID id;
    private String task;
    private boolean completed;
}
