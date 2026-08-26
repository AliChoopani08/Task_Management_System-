package com.Ali_Choopani.Task_Management_System.services.user;

import com.Ali_Choopani.Task_Management_System.dto.user.AuthResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.LoginRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.UserViewSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RegisterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    AuthResponse register(RegisterRequest request, String userAgent);
    AuthResponse login(LoginRequest request, UUID deviceUuid, String userAgent);
    Page<UserViewSummary> searchUsersByFullNameOrEmail(String fullNameOrEmail, Pageable pageable);
}
