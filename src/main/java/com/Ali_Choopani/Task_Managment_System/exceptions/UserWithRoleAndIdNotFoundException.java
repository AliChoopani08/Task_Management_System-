package com.Ali_Choopani.Task_Managment_System.exceptions;

import com.Ali_Choopani.Task_Managment_System.entities.ProjectRole;

import static java.lang.String.format;

public class UserWithRoleAndIdNotFoundException extends RuntimeException {

    public UserWithRoleAndIdNotFoundException(Long userId, ProjectRole role) {
        super(format("Not found any user with id [%s] and role [%s] !", userId, role));
    }
}
