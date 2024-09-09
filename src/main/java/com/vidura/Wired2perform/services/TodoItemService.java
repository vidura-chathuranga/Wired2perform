package com.vidura.Wired2perform.services;

import com.vidura.Wired2perform.dto.TodoListDto;
import com.vidura.Wired2perform.dto.TodoListItemDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.models.TodoItem;
import com.vidura.Wired2perform.models.TodoList;
import com.vidura.Wired2perform.repositories.TodoItemRepository;
import com.vidura.Wired2perform.repositories.TodoListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final TodoListRepository todoListRepository;
    private final ModelMapper modelMapper;

    public TodoItemService(TodoItemRepository todoItemRepository, TodoListRepository todoListRepository, ModelMapper modelMapper) {
        this.todoItemRepository = todoItemRepository;
        this.todoListRepository = todoListRepository;
        this.modelMapper = modelMapper;
    }

    //add an todoItem to specific todoList
    public GenericResponse<TodoListItemDto> addTodoItem(UUID todoListId, TodoListItemDto todoListItemDto){

        //check todo list is existing
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo list not found"));

        // create todoList Item entity
        TodoItem item = new TodoItem();
        item.setTask(todoListItemDto.getTask());
        item.setTodoList(todoList);

        todoItemRepository.save(item);

        return new GenericResponse<>("Todo item added successfully", modelMapper.map(item, TodoListItemDto.class));
    }

    //update todo list item information
    public GenericResponse<TodoListItemDto> updateTodoItem(UUID todoItemId, TodoListItemDto todoListItemDto){

        //check if the todo item is existing
        TodoItem item  = todoItemRepository.findById(todoItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo Item not found"));


        //then change the todo item completed state to true of false
        item.setTask(todoListItemDto.getTask());
        item.setCompleted(todoListItemDto.isCompleted());

        //save the existing object with changed completed state
        todoItemRepository.save(item);


        return new GenericResponse<>("Todo Item marked as " + (item.isCompleted() ? "completed" : "inCompleted"),
                modelMapper.map(item, TodoListItemDto.class));
    }

    //remove todoItem from todoList
    public GenericResponse<TodoListItemDto> removeTodoItem(UUID todoItemId){

        //todo item is existing or not
        TodoItem item = todoItemRepository.findById(todoItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo list item not found"));

        //remove todo item from todoList
        todoItemRepository.deleteById(todoItemId);

        return new GenericResponse<>("Todo item removed successfully", null);
    }
}
