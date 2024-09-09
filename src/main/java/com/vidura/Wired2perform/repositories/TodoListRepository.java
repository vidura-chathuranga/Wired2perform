package com.vidura.Wired2perform.repositories;

import com.vidura.Wired2perform.models.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TodoListRepository extends JpaRepository<TodoList, UUID> {

    @Query("SELECT t FROM TodoList t WHERE t.archived = false  AND t.userEntity.id = :userId")
    List<TodoList> findAllByUserId(UUID userId);

    @Query(value = "SELECT t FROM TodoList t WHERE t.archived = true order by t.updatedAt")
    List<TodoList> findAllByArchived();
}
