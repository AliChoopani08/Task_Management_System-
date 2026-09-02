package com.Ali_Choopani.Task_Management_System.exceptions.task;

public class NotFoundTaskAndAssignee extends RuntimeException {
    public NotFoundTaskAndAssignee(Long taskId, Long assigneeId) {
        super(String.format("Not found any task with id [%d] and assignee with id [%d] !", taskId, assigneeId));
    }
}
