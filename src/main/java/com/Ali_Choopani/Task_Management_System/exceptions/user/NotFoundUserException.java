package com.Ali_Choopani.Task_Management_System.exceptions.user;

import static java.lang.String.format;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(Long userId) {
        super(format("Not found any user with this id [%s] !", userId));
    }
}
