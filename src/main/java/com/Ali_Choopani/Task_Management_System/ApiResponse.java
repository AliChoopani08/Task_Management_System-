package com.Ali_Choopani.Task_Management_System;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public record ApiResponse<T>(Integer status, String message, T data, LocalDateTime time) {

    public ApiResponse(Integer status, String message, T data) {
        this(status, message, data, now());
    }
}
