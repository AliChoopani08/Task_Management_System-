package com.Ali_Choopani.Task_Management_System.repositories;

import com.Ali_Choopani.Task_Management_System.dto.task.MyTasksSummary;
import com.Ali_Choopani.Task_Management_System.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByAssigneeIdAndTitleIgnoreCase(Long assigneeId, String title);
    Optional<Task> findByProjectIdAndTitleIgnoreCase(Long projectId, String title);
    Optional<Task> findByProjectIdAndId(Long projectId, Long id);
    @Query("""
            SELECT t 
            FROM Task t
            JOIN t.assignee a
            JOIN a.member m
            WHERE t.id = :taskId AND m.id = :assigneeId
            """)
    @EntityGraph(attributePaths = "assignee")
    Optional<Task> findByIdAndAssigneeId(@Param("taskId") Long id, @Param("assigneeId") Long assigneeId);

    @Query("""
            SELECT new com.Ali_Choopani.Task_Management_System.dto.task.MyTasksSummary(t.id, t.title, t.status)
            FROM Task t
            JOIN t.assignee a
            JOIN a.member m
            WHERE m.id = :userId
            """)
    Page<MyTasksSummary> findByUserIdAndReturnTasksSummary(@Param("userId") Long userId, Pageable pageable);
}
