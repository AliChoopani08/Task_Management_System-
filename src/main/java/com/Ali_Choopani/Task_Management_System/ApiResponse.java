package com.Ali_Choopani.Task_Management_System;

import java.time.LocalDateTime;

public record ApiResponse<T>(Integer status, String message, T data, LocalDateTime time) {
}
