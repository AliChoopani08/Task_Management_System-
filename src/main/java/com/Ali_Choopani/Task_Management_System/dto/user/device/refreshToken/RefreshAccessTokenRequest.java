package com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class RefreshAccessTokenRequest {

    @NotNull(message = "Refresh token can't be null or empty !")
    private UUID refreshToken;
}
