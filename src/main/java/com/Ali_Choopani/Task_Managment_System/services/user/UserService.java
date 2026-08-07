package com.Ali_Choopani.Task_Managment_System.services.user;

import com.Ali_Choopani.Task_Managment_System.dto.AuthResponse;
import com.Ali_Choopani.Task_Managment_System.dto.LoginRequest;
import com.Ali_Choopani.Task_Managment_System.dto.RegisterRequest;

import java.util.UUID;

public interface UserService {
    AuthResponse register(RegisterRequest request, String userAgent);
    AuthResponse login(LoginRequest request, UUID deviceUuid, String userAgent);
}
