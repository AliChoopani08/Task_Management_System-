package com.Ali_Choopani.Task_Managment_System.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class NotFoundRefreshToken extends RuntimeException {
    public NotFoundRefreshToken(UUID token) {
        super(format("Not found this refresh token with this token [%s] !", token));
    }
}
