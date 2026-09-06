package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.CompleteOrUpdateProfileFieldsRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.user.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProfileSummary>> completeProfileFields(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                             @RequestBody @Valid CompleteOrUpdateProfileFieldsRequest request) {
        final ProfileSummary serviceResponse = service.completeOrUpdateProfileFields(request, currentUser.getId());

        return status(OK)
                .body(new ApiResponse<>(OK.value(), "Profile Fields Were Completed Successfully", serviceResponse, now()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProfileSummary>> updateProfileFields(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                           @RequestBody @Valid CompleteOrUpdateProfileFieldsRequest request) {
        final ProfileSummary serviceResponse = service.completeOrUpdateProfileFields(request, currentUser.getId());

        return ok(new ApiResponse<>(OK.value(), "Profile fields were successfully updated", serviceResponse, now()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileSummary>> displayUserProfileView(@AuthenticationPrincipal UserDetailImpl currentUser) {
        final ProfileSummary serviceResponse = service.displayUserProfileView(currentUser.getId());

        return ok(new ApiResponse<>(OK.value(), "User's profile view was successfully returned", serviceResponse, now()));
    }
}
