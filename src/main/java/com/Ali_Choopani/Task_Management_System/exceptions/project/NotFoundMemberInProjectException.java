package com.Ali_Choopani.Task_Management_System.exceptions.project;

public class NotFoundMemberInProjectException extends RuntimeException {
    public NotFoundMemberInProjectException(Long memberId, Long projectId) {
        super(String.format("Not found member with id [%d] in project with id [%d] !", memberId, projectId));
    }
}
