package com.Ali_Choopani.Task_Managment_System.exceptions;

import static java.lang.String.format;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(Long id) {
        super(format("Not found any user with this id [%s} !", id));
    }
}
