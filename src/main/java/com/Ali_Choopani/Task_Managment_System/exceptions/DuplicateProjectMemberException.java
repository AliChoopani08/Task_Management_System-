package com.Ali_Choopani.Task_Managment_System.exceptions;

import static java.lang.String.format;

public class DuplicateProjectMemberException extends RuntimeException {
    public DuplicateProjectMemberException(Long memberId, Long projectId) {
        super(format("User with id [%s] is already an active member of project with id  [%s] !", memberId, projectId));
    }
}
