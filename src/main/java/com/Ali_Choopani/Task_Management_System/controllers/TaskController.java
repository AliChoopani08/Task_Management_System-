package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.task.CreateTaskRequest;
import com.Ali_Choopani.Task_Management_System.dto.task.TaskSummary;
import com.Ali_Choopani.Task_Management_System.dto.task.UserTasksSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.task.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
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

    @PreAuthorize("@projectAuthorization.isManager(authentication)")
    @PatchMapping("project/{projectId}/task/{taskId}/member/{memberId}")
    public ResponseEntity<ApiResponse<TaskSummary>> assignTaskToProjectMember(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                              @PathVariable Long projectId,
                                                                              @PathVariable Long taskId,
                                                                              @PathVariable Long memberId) {
        final TaskSummary serviceResponse = service.assignToProjectMember(taskId, projectId, memberId, currentUser.getId());

        return ok(new ApiResponse<>(OK.value(), "Task was assigned to project member successfully", serviceResponse, now()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserTasksSummary>> getMyTasksSummary(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                           @ParameterObject
                                                                           @PageableDefault(sort = "title", direction = DESC)
                                                                           Pageable pageable) {
        final UserTasksSummary serviceResponse = service.getUserTasksSummary(currentUser.getId(), pageable);

        return ok(new ApiResponse<>(OK.value(), "User's tasks summary returned successfully", serviceResponse, now()));
    }
}
