package com.Ali_Choopani.Task_Management_System.exceptions.user;

public class DuplicateUsername extends RuntimeException {
    public DuplicateUsername() {
        super("This username is already registered ! Please login with this identifier");
    }
}
