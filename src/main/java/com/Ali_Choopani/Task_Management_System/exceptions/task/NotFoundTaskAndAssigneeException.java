package com.Ali_Choopani.Task_Management_System.exceptions.task;

public class NotFoundTaskAndAssigneeException extends RuntimeException {
    public NotFoundTaskAndAssigneeException(Long taskId, Long assigneeId) {
        super(String.format("Not found any task with id [%d] and assignee with id [%d] !", taskId, assigneeId));
    }
}
