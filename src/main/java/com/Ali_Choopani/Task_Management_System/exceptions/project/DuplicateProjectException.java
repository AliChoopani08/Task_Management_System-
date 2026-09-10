package com.Ali_Choopani.Task_Management_System.exceptions.project;

import static java.lang.String.format;

public class DuplicateProjectException extends RuntimeException {

    public DuplicateProjectException(Long managerId, String projectTitle) {
        super(format("User with id [%d] has an active project with title [%s] and it is duplicate !",managerId, projectTitle ));
    }
}
