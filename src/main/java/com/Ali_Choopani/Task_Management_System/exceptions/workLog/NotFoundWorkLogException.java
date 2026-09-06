package com.Ali_Choopani.Task_Management_System.exceptions.workLog;

import static java.lang.String.format;

public class NotFoundWorkLogException extends RuntimeException {
    public NotFoundWorkLogException(Long id) {
        super(format("Not found any work log with id [%d] !", id));
    }
}
