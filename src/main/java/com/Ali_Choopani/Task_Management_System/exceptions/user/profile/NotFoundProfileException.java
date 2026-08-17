package com.Ali_Choopani.Task_Management_System.exceptions.user.profile;

import static java.lang.String.format;

public class NotFoundProfileException extends RuntimeException {
    public NotFoundProfileException(Long userId) {
        super(format("Not found any profile of user with id [%s] !", userId));
    }
}
