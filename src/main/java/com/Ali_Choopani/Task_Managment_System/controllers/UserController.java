package com.Ali_Choopani.Task_Managment_System.controllers;

import com.Ali_Choopani.Task_Managment_System.ApiResponse;
import com.Ali_Choopani.Task_Managment_System.dto.user.AuthResponse;
import com.Ali_Choopani.Task_Managment_System.dto.user.LoginRequest;
import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RegisterRequest;
import com.Ali_Choopani.Task_Managment_System.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/auth")
    @Operation(security =
            {@SecurityRequirement(name = "")})
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request, HttpServletRequest httpRequest) {
        final String userAgent = httpRequest.getHeader("User-Agent");

        final AuthResponse response = service.register(request, userAgent);

        return status(CREATED)
                .body(new ApiResponse<>(CREATED.value(), "A new user registered successfully", response, now()));
    }

    @PostMapping("/login")
    @Operation(description = "If you have a available device, -> a new refresh token is generated for it." +
            "Otherwise, -> a new device is created for you and new refresh token connects to it too",
            security = {@SecurityRequirement(name = "")})
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request, @Parameter(description = "If you don't have it, leave it empty") @RequestHeader(name = "X-Device-UUID", required = false)UUID deviceUuid, HttpServletRequest httpRequest) {
        final String userAgent = httpRequest.getHeader("User-Agent");

        final AuthResponse response = service.login(request, deviceUuid, userAgent);

        return status(OK)
                .body(new ApiResponse<>(OK.value(), "User longed in successfully", response, now()));
    }

    }
