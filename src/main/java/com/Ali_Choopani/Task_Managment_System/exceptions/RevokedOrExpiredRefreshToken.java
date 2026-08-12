package com.Ali_Choopani.Task_Managment_System.exceptions;

import java.util.UUID;

public class RevokedOrExpiredRefreshToken extends RuntimeException {
    public RevokedOrExpiredRefreshToken(UUID token) {
        super(String.format("Refresh token with token [%s] has been revoked or has expired !", token));
    }
}
