package com.Ali_Choopani.Task_Management_System.exceptions.task;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(Long taskId) {
        super(String.format("Not found task with id [%d] !", taskId));
    }
}
