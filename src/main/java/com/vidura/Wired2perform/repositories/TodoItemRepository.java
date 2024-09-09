package com.vidura.Wired2perform.repositories;

import com.vidura.Wired2perform.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {

}
