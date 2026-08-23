package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.project.AddNewProjectMemberRequest;
import com.Ali_Choopani.Task_Management_System.dto.project.CreateProjectRequest;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectMemberSummary;
import com.Ali_Choopani.Task_Management_System.dto.project.ProjectSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectSummary>> createANewProject(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                         @RequestBody @Valid CreateProjectRequest request) {
        final ProjectSummary methodResponse = service.createAProject(request, currentUser.getId());

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A New Project Was Created Successfully", methodResponse, now()));
    }

    @PreAuthorize("@projectAuthorization.isManager(authentication)")
    @PostMapping("/{projectId}/member/{memberId}")
    @Operation(description = "This endpoint is only accessible for project manager !")
    public ResponseEntity<ApiResponse<ProjectMemberSummary>> addNewProjectMember(@AuthenticationPrincipal UserDetailImpl currentManager,
                                                                                 @PathVariable("projectId") Long projectId,
                                                                                 @PathVariable("memberId") Long memberId,
                                                                                 @RequestBody @Valid AddNewProjectMemberRequest request) {
        final ProjectMemberSummary response = service.addProjecetMember(projectId, currentManager.getId(), memberId, request);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new member added to project successfully", response, now()));
    }
}
