package com.Ali_Choopani.Task_Managment_System.exceptions;

import static java.lang.String.format;

public class InvalidDevice extends RuntimeException{
    public InvalidDevice(Long deviceId) {
        super(format("This [{%s}] device does not exists or it is unavailable !", deviceId));
    }
}
