package com.Ali_Choopani.Task_Management_System.exceptions.project;

public class NotFoundProjectException extends RuntimeException {
    public NotFoundProjectException(Long projectId) {
        super(String.format("Not found any project with this id [%d] !", projectId));
    }
}
