package com.Ali_Choopani.Task_Management_System;

import java.time.LocalDateTime;

public record ErrorResponse (int status, String error, String message,String path, LocalDateTime time){
}
