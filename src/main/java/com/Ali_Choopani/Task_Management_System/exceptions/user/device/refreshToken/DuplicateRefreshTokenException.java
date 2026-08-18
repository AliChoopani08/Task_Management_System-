package com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken;

import static java.lang.String.format;

public class DuplicateRefreshTokenException extends RuntimeException {
    public DuplicateRefreshTokenException(Long deviceId , Long tokenId) {
        super(format("This device [%s] has a valid refresh token [%s], Pls enter with this refresh token !" ,deviceId, tokenId));
    }
}
