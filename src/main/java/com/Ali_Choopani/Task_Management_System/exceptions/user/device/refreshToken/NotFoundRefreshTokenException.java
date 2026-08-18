package com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken;

import java.util.UUID;

import static java.lang.String.format;

public class NotFoundRefreshTokenException extends RuntimeException {
    public NotFoundRefreshTokenException(UUID token) {
        super(format("Not found this refresh token with this token [%s] !", token));
    }
}
