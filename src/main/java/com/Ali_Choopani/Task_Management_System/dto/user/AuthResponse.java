package com.Ali_Choopani.Task_Management_System.dto.user;

import com.Ali_Choopani.Task_Management_System.dto.user.device.DeviceSummary;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse (UserSummary user, DeviceSummary device, @JsonProperty("refresh token") RefreshTokenSummary refreshTokenSummary, @JsonProperty("access token") String accessToken){
}
