package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.workLog.CreateWorkLogRequest;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogDetails;
import com.Ali_Choopani.Task_Management_System.dto.workLog.WorkLogSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.workLog.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work-log")
public class WorkLogController {

    private final WorkLogService service;

    @PostMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<WorkLogDetails>> createWorkLog(@AuthenticationPrincipal UserDetailImpl currentUser, @PathVariable Long taskId,
                                                                     @RequestBody @Valid CreateWorkLogRequest request) {
        final WorkLogDetails serviceResponse = service.createWorkLog(currentUser.getId(), taskId, request);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new work log for task was created successfully", serviceResponse, now()));
    }

    @GetMapping("/task-id/{taskId}/my-work-logs/summary")
    public ResponseEntity<ApiResponse<Set<WorkLogSummary>>> getMyWorkLogsSummary(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                                 @PathVariable Long taskId) {
        final Set<WorkLogSummary> serviceResponse = service.getWorkLogsSummaryOfFoundTaskAndUser(taskId, currentUser.getId());

        return ok(new ApiResponse<>(OK.value(), "The task work logs summary of user were successfully returned", serviceResponse, now()));
    }
}
