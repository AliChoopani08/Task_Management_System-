package com.Ali_Choopani.Task_Management_System.dto.user;

import com.Ali_Choopani.Task_Management_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;

public record AuthResponse(UserSummary user,
                           DeviceSummary device,
                           RefreshTokenSummary refreshTokenSummary,
                           String accessToken) {
}
