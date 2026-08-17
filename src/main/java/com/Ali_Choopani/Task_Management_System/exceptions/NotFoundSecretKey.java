package com.Ali_Choopani.Task_Management_System.exceptions;

import static java.lang.String.format;

public class NotFoundSecretKey extends RuntimeException {
    public NotFoundSecretKey(String name) {
        super(format("This secret key [%s] is empty or didn't exist !", name));
    }
}
