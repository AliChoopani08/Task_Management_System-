package com.Ali_Choopani.Task_Managment_System.exceptions;

public class DuplicateUsername extends RuntimeException {
    public DuplicateUsername() {
        super("This username is already registered ! Please login with this identifier");
    }
}
