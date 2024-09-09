package com.vidura.Wired2perform.controllers;

import com.vidura.Wired2perform.dto.TodoListDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.models.TodoList;
import com.vidura.Wired2perform.services.TodoListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoListController {

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

//    get all todos for a particular user
    @GetMapping("/{userId}")
    public ResponseEntity<GenericResponse<List<TodoListDto>>> getAllTodoList(@PathVariable UUID userId){
        return ResponseEntity.ok(todoListService.getAllTodoLists(userId));
    }

// create todoList for a particular user
    @PostMapping("/{userId}")
    public ResponseEntity<GenericResponse<TodoListDto>> saveTodoList(@RequestBody TodoListDto todoList, @PathVariable UUID userId){
        GenericResponse<TodoListDto> response = todoListService.saveTodos(todoList, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //update todoList
    @PutMapping("/{userId}/{todoListId}")
    public ResponseEntity<GenericResponse<TodoListDto>>updateTodoList(@RequestBody TodoListDto todoList, @PathVariable UUID todoListId, @PathVariable UUID userId){
        return ResponseEntity.ok(todoListService.updateTodoList(todoList,todoListId, userId));
    }

    //delete specific todoList
    @DeleteMapping("/{userId}/{todoListId}")
    public ResponseEntity<GenericResponse<TodoListDto>> deleteTodoList(@PathVariable UUID userId, @PathVariable UUID todoListId) {
        return ResponseEntity.ok(todoListService.removeTodoList(todoListId,userId));
    }
}
