package com.vidura.Wired2perform.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Task is required")
    private String task;

    @ColumnDefault("false")
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;
}
