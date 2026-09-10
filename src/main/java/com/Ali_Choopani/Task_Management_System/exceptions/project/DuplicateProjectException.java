package com.Ali_Choopani.Task_Management_System.exceptions.project;

import static java.lang.String.format;

public class DuplicateProjectException extends RuntimeException {

    public DuplicateProjectException(Long memberId, Long projectId) {
        super(format("User with id [%s] is already an active member of project with id  [%s] !", memberId, projectId));
    }
}
