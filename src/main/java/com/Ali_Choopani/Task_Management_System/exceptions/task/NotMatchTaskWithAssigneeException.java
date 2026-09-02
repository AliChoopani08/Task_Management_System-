package com.Ali_Choopani.Task_Management_System.exceptions.task;

public class NotMatchTaskWithAssignee extends RuntimeException {
    public NotMatchTaskWithAssignee(Long taskId, Long assigneeId) {
        super(String.format("This task with id [%d] is not match with assignee with id [%d] !", taskId, assigneeId));
    }
}
