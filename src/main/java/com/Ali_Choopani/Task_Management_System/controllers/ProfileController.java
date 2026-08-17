package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.CompleteOrUpdateProfileRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.profile.ProfileSummary;
import com.Ali_Choopani.Task_Management_System.security.UserDetailImpl;
import com.Ali_Choopani.Task_Management_System.services.user.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProfileSummary>> completeProfileFields(@AuthenticationPrincipal UserDetailImpl currentUser,
                                                                             @RequestBody @Valid CompleteOrUpdateProfileRequest request) {
        final ProfileSummary methodResponse = service.completeProfileFields(request, currentUser.getId());

        return status(OK)
                .body(new ApiResponse<>(OK.value(), "Profile Fields Were Completed Successfully", methodResponse, now()));
    }
}
