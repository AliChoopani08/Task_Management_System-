package com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RefreshAccessTokenResponse(@JsonProperty("user id")Long userId,
                                         @JsonProperty("device uuid")UUID deviceUuid,
                                         @JsonProperty("refresh token")UUID refreshToken,
                                         @JsonProperty("access token")String accessToken) {
}
