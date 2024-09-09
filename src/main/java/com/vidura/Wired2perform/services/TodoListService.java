package com.vidura.Wired2perform.services;

import com.vidura.Wired2perform.dto.TodoListDto;
import com.vidura.Wired2perform.dto.UserDto;
import com.vidura.Wired2perform.models.GenericResponse;
import com.vidura.Wired2perform.models.TodoItem;
import com.vidura.Wired2perform.models.TodoList;
import com.vidura.Wired2perform.models.UserEntity;
import com.vidura.Wired2perform.repositories.TodoListRepository;
import com.vidura.Wired2perform.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public TodoListService(TodoListRepository todoListRepository, UserRepository userRepository, ModelMapper modelMapper){
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public GenericResponse<TodoListDto> saveTodos(TodoListDto todos,UUID usrId ) {

        UserEntity user = userRepository.findById(usrId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        TodoList todoList = new TodoList();
        todoList.setTitle(todos.getTitle());
        todoList.setUserEntity(user);


        //handle TodoItems if provided
        if(todos.getTodoItems() != null){
            List<TodoItem> items = todos.getTodoItems().stream()
                    .map(itemDto -> {
                       TodoItem item = new TodoItem();
                       item.setTask(itemDto.getTask());
                       item.setTodoList(todoList);
                       return item;
                    }).collect(Collectors.toList());
            todoList.setTodoItems(items);
        }


        todoListRepository.save(todoList);

        TodoListDto responseDto = modelMapper.map(todoList, TodoListDto.class);

        return new GenericResponse<>("TodoList created successfully", responseDto);
    }

    //update todoList
    public GenericResponse<TodoListDto> updateTodoList(TodoListDto todoListDto, UUID todoListId, UUID userId){

        //get user information
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // check existing todoList
        TodoList existingTodo = todoListRepository.findById(todoListId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo list not found"));


        if(!existingTodo.getUserEntity().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to perform this action");
        }

        existingTodo.setTitle(todoListDto.getTitle());
        existingTodo.setArchived(todoListDto.isArchived());

        todoListRepository.save(existingTodo);

        TodoListDto updatedTodoListDto = modelMapper.map(existingTodo,TodoListDto.class);

        return new GenericResponse<>("TodoList updated successfully", updatedTodoListDto);

    }

    //get all todoList
    public GenericResponse<List<TodoListDto>> getAllTodoLists(UUID userId) {

        List<TodoList> todos = todoListRepository.findAllByUserId(userId);

        //convert todos into todoDTO
        List<TodoListDto> todoListDto = todos.stream()
                .map(todoList -> modelMapper.map(todoList, TodoListDto.class))
                .collect(Collectors.toList());


        return new GenericResponse<>("TodoLists fetched Successfully", todoListDto);
    }

    //delete specific todoList
    public GenericResponse<TodoListDto> removeTodoList(UUID todoListId, UUID userId){

        //check user existing
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        //check todoList existing
        TodoList todoList = todoListRepository.findById(todoListId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Todo List not found"));

        //check todoList created user and requested user ids are same
        if(!todoList.getUserEntity().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to perform this action");
        }

        //remove todoList
        todoListRepository.deleteById(todoListId);

        return new GenericResponse<>("todoList deleted Successfully", null);
    }
}
