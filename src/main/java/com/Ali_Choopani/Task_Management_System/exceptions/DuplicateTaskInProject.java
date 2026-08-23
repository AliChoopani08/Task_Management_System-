package com.Ali_Choopani.Task_Management_System.exceptions;

public class DuplicateTaskInProject extends RuntimeException {
    public DuplicateTaskInProject(String taskTitle, Long projectId) {
        super(String.format("A task with this title [%s] is already in project id [%d] !", taskTitle, projectId));
    }
}
