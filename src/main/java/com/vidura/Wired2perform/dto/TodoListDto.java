package com.vidura.Wired2perform.dto;

import com.vidura.Wired2perform.models.TodoItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoListDto {
    private UUID id;
    private String title;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TodoListItemDto> todoItems;
}
