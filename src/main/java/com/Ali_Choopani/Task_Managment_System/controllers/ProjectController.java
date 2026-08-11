package com.Ali_Choopani.Task_Managment_System.controllers;

import com.Ali_Choopani.Task_Managment_System.ApiResponse;
import com.Ali_Choopani.Task_Managment_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Managment_System.dto.project.ProjectMemberSummary;
import com.Ali_Choopani.Task_Managment_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Managment_System.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberSummary>> createANewProject(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                               @RequestBody @Valid CreateProjectRequest request) {
        final ProjectMemberSummary methodResponse = service.createAProject(request, currentUser.getId());

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A New Project Was Created Successfully", methodResponse, now()));
    }
}
