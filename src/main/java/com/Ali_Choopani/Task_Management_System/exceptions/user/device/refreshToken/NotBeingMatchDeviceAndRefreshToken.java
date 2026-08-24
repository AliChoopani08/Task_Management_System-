package com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken;

import java.util.UUID;

import static java.lang.String.format;

public class NotBeingMatchDeviceAndRefreshToken extends RuntimeException {
    public NotBeingMatchDeviceAndRefreshToken() {
        super("This device uuid is not match with entered refresh token");
    }
}
