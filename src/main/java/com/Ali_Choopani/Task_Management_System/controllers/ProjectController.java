package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.project.*;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
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
    public ResponseEntity<ApiResponse<ProjectMembersDetails>> addNewProjectMember(@AuthenticationPrincipal UserDetailImpl currentManager,
                                                                           @PathVariable("projectId") Long projectId,
                                                                           @PathVariable("memberId") Long memberId,
                                                                           @RequestBody @Valid AddNewProjectMemberRequest request,
                                                                                  @ParameterObject
                                                                                  @PageableDefault(size = 20, sort = "profile.firstName")
                                                                                  Pageable pageable) {
        final ProjectMembersDetails response = service.addProjectMember(projectId, currentManager.getId(), memberId, request, pageable);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new member added to project successfully", response, now()));
    }

    @GetMapping("/my/projects-summary")
    public ResponseEntity<ApiResponse<Set<MyProjectsSummary>>> getMyProjectsSummary(@AuthenticationPrincipal UserDetailImpl currentUser) {
        final Set<MyProjectsSummary> serviceResponse = service.getMyProjectsSummary(currentUser.getId());

        return ok(new ApiResponse<>(OK.value(), "User's projects were returned successfully", serviceResponse, now()));
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<ApiResponse<ProjectMembersDetails>> getProjectMembersDetails(@PathVariable Long projectId,
                                                                                       @ParameterObject
                                                                                       @PageableDefault(size = 20, sort = "member.profile.firstName")Pageable pageable) {
        final ProjectMembersDetails serviceResponse = service.getProjectMembersDetails(projectId, pageable);

        return ok(new ApiResponse<>(OK.value(), "Project members details were returned successfully", serviceResponse, now()));
    }
}
