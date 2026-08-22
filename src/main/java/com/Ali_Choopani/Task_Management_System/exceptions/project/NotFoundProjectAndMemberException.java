package com.Ali_Choopani.Task_Management_System.exceptions.project;

import com.Ali_Choopani.Task_Management_System.entities.ProjectRole;

public class NotFoundProjectAndMemberException extends RuntimeException{

    public NotFoundProjectAndMemberException(Long projectId, Long memberId, ProjectRole memberRole) {
        super(String.format("Not found any project with id [%d] and member with id [%d] and role [%s] !", projectId, memberId, memberRole));
    }
}
