package com.Ali_Choopani.Task_Managment_System.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AuthResponse (UserSummary user, DeviceSummary device, @JsonProperty("refresh token")RefreshTokenSummary refreshTokenSummary, @JsonProperty("access token") String accessToken){
}
