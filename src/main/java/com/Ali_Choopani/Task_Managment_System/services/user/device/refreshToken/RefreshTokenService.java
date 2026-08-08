package com.Ali_Choopani.Task_Managment_System.services.user.device.refreshToken;

import com.Ali_Choopani.Task_Managment_System.dto.user.device.refreshToken.RefreshTokenSummary;

public interface RefreshTokenService {

    RefreshTokenSummary createNewToken(Long deviceId);
}
