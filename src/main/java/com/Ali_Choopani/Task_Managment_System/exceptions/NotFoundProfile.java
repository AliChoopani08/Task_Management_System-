package com.Ali_Choopani.Task_Managment_System.exceptions;

import static java.lang.String.format;

public class NotFoundProfile extends RuntimeException {
    public NotFoundProfile(Long userId) {
        super(format("Not found any profile of user with id [%s] !", userId));
    }
}
