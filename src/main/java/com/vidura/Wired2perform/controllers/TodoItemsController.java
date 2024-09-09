package com.vidura.Wired2perform.controllers;


import com.vidura.Wired2perform.dto.TodoListItemDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.services.TodoItemService;
import com.vidura.Wired2perform.services.TodoListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/todos/items")
public class TodoItemsController {

    private final TodoItemService todoItemService;

    public TodoItemsController(TodoItemService todoItemService){
        this.todoItemService = todoItemService;
    }

    //add an item to specific todoList
    @PostMapping("/{todoListId}")
    public ResponseEntity<GenericResponse<TodoListItemDto>> addTodoListItem(@PathVariable UUID todoListId, @RequestBody TodoListItemDto todoListItem){
        return ResponseEntity.ok(todoItemService.addTodoItem(todoListId, todoListItem));
    }
    //update todo item information
    @PutMapping("/{todoItemId}")
    public ResponseEntity<GenericResponse<TodoListItemDto>> updateTodoItem(@PathVariable UUID todoItemId, @RequestBody TodoListItemDto todoListItemDto){
        return ResponseEntity.ok(todoItemService.updateTodoItem(todoItemId, todoListItemDto));
    }

    //remove todoItem from todoList
    @DeleteMapping("/{todoItemId}")
    public ResponseEntity<GenericResponse<TodoListItemDto>> removeTodoItem(@PathVariable UUID todoItemId){
        return ResponseEntity.ok(todoItemService.removeTodoItem(todoItemId));
    }
}
