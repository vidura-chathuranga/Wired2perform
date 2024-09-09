package com.vidura.Wired2perform.controllers;

import com.vidura.Wired2perform.dto.TodoListDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.services.ArchivedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos/archived")
public class ArchivedController {

    private final ArchivedService archivedService;

    public ArchivedController(ArchivedService archivedService) {
        this.archivedService = archivedService;
    }

    //add todoList to archived
    @PutMapping("/{todoListId}/add")
    public ResponseEntity<GenericResponse<TodoListDto>> makeTodoListArchived(@PathVariable UUID todoListId){
        return ResponseEntity.ok(archivedService.makeTodoListArchived(todoListId));
    }
    //get all archive TodoLists
    @GetMapping("")
    public ResponseEntity<GenericResponse<List<TodoListDto>>> getAllArchivedTodos(){
        return ResponseEntity.ok(archivedService.getAllArchivedTodos());
    }

    // removed todoList from archived list
    @PutMapping("/{todoListId}/remove")
    public ResponseEntity<GenericResponse<TodoListDto>> removeTodoListArchived(@PathVariable UUID todoListId){
        return ResponseEntity.ok(archivedService.removeTodoListArchived(todoListId));
    }
}
