package com.Ali_Choopani.Task_Management_System.exceptions.user.profile;

import static java.lang.String.format;

public class ProfileNotCompletedException extends RuntimeException {
    public ProfileNotCompletedException(Long userId) {
        super(format("Profile of user with id [%s] hasn't completed, Pls complete its fields !", userId));
    }
}
