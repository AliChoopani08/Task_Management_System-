package com.Ali_Choopani.Task_Management_System.controllers;

import com.Ali_Choopani.Task_Management_System.ApiResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenResponse;
import com.Ali_Choopani.Task_Management_System.services.user.device.refreshToken.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService service;

    @PostMapping("/access_token")
    public ResponseEntity<ApiResponse<RefreshAccessTokenResponse>> refreshAccessTokenByRefreshTokenAndDeviceUuid
            (@RequestHeader(name = "X-Device-UUID")UUID deviceUuid, @RequestBody @Valid RefreshAccessTokenRequest request) {

        final RefreshAccessTokenResponse responseMethod = service.refreshAccessToken(deviceUuid, request);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new access token was generated successfully", responseMethod, now()));
    }
}
