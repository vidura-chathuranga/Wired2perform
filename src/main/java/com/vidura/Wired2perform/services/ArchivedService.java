package com.vidura.Wired2perform.services;

import com.vidura.Wired2perform.dto.TodoListDto;
import com.vidura.Wired2perform.dto.UserDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.models.TodoList;
import com.vidura.Wired2perform.repositories.TodoListRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ArchivedService {

    private final TodoListRepository todoListRepository;
    private final ModelMapper modelMapper;

    public ArchivedService(TodoListRepository todoListRepository, ModelMapper modelMapper) {
        this.todoListRepository = todoListRepository;
        this.modelMapper = modelMapper;
    }

    //add todoList to archived
    public GenericResponse<TodoListDto> makeTodoListArchived(UUID todoListId){
        //check todolist existing
        TodoList existingTodoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo list not found"));

        //change todolist archived state to true
        existingTodoList.setArchived(true);

        //save updated todo list
        todoListRepository.save(existingTodoList);

        return new GenericResponse<>("Todo list added to archived", modelMapper.map(existingTodoList, TodoListDto.class));
    }

    //get all archive TodoLists
    public GenericResponse<List<TodoListDto>> getAllArchivedTodos() {
        List<TodoList> todoLists = todoListRepository.findAllByArchived();

        return new GenericResponse<>("Archived todos are fetched",modelMapper.map(todoLists, new TypeToken<List<TodoListDto>>(){}.getType()));
    }

    // removed todoList from archived list
    public GenericResponse<TodoListDto> removeTodoListArchived(UUID todoListId) {

        //checking todolist exising
        TodoList existingTodo = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Todo list not found"));

        existingTodo.setArchived(false);

        //save updated existing todo
        todoListRepository.save(existingTodo);

        return new GenericResponse<>("Todo lost removed from archived", modelMapper.map(existingTodo, TodoListDto.class));

    }
}
