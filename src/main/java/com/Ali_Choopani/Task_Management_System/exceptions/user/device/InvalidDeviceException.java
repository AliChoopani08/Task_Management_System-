package com.Ali_Choopani.Task_Management_System.exceptions.user.device;

import static java.lang.String.format;

public class InvalidDeviceException extends RuntimeException{
    public InvalidDeviceException(Long deviceId) {
        super(format("This [{%s}] device does not exists or it is unavailable !", deviceId));
    }
}
