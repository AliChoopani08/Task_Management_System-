package com.Ali_Choopani.Task_Managment_System.exceptions;

public class InsufficientInformationException extends RuntimeException {
    public InsufficientInformationException() {
        super("At least one of the email Or phone number must be entered for registering !");
    }
}
