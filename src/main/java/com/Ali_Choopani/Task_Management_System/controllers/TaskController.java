package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.task.TaskService;
import com.Ali_Choopani.Task_Management_System.services.task.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.EntityReference;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PreAuthorize("@projectAuthorization.isManager(authentication)")
    @PostMapping("project/{projectId}")
    public ResponseEntity<ApiResponse<TaskSummary>> createTask(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                               @PathVariable("projectId") Long projectId, @RequestBody @Valid CreateTaskRequest request) {
        final TaskSummary responseService = service.createANewTaskOfProject(projectId, currentUser.getId(), request);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new task in project was created successfully", responseService, now()));
    }

}
