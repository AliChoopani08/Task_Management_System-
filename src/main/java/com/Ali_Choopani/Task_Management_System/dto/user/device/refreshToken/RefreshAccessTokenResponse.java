package com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RefreshAccessTokenResponse(Long userId,
                                         UUID deviceUuid,
                                         UUID refreshToken,
                                         String accessToken) {
}
