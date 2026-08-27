package com.Ali_Choopani.Task_Management_System.repositories;

import com.Ali_Choopani.Task_Management_System.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByAssigneeIdAndTitleIgnoreCase(Long assignee, String title);
    Optional<Task> findByProjectIdAndTitleIgnoreCase(Long projectId, String title);
    Optional<Task> findByProjectIdAndId(Long projectId, Long id);
}
