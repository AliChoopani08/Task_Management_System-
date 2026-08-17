package com.Ali_Choopani.Task_Management_System.exceptions.project;

import static java.lang.String.format;

public class DuplicateProjectMemberException extends RuntimeException {
    public DuplicateProjectMemberException(Long memberId, Long projectId) {
        super(format("User with id [%s] is already an active manager of project with id  [%s] !", memberId, projectId));
    }
}
