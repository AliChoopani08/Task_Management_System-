package com.Ali_Choopani.Task_Managment_System.exceptions;

import static java.lang.String.format;

public class NotFoundUser extends RuntimeException {
    public NotFoundUser(Long userId) {
        super(format("Not found any user with this identifier [%s] !", userId));
    }
}
