package com.Ali_Choopani.Task_Management_System.services.user.device.refreshToken;

import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenResponse;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshAccessTokenRequest;
import com.Ali_Choopani.Task_Management_System.dto.user.device.refreshToken.RefreshTokenSummary;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshTokenSummary createNewToken(Long deviceId);
    RefreshAccessTokenResponse refreshAccessToken(UUID deviceUuid, RefreshAccessTokenRequest request);

}
