package com.Ali_Choopani.Task_Management_System.exceptions.user.device;

import static java.lang.String.format;

public class BlockedDeviceException extends RuntimeException {
    public BlockedDeviceException(Long id) {
        super(format("This device with id [%d] has been blocked !", id));
    }
}
