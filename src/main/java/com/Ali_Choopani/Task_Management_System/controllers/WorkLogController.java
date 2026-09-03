package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.comment.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.comment.WorkLogSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.comment.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work-log")
public class WorkLogController {

    private final WorkLogService service;

    @PostMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<WorkLogSummary>> createWorkLog(@AuthenticationPrincipal UserDetailImpl currentUser, @PathVariable Long taskId,
                                                                     @RequestBody @Valid CreateWorkLogRequest request) {
        final WorkLogSummary serviceResponse = service.createWorkLog(currentUser.getId(), taskId, request);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new work log for task was created successfully", serviceResponse, now()));
    }
}
