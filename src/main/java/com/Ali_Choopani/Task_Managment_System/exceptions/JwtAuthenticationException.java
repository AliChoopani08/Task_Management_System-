package com.Ali_Choopani.Task_Managment_System.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {


  public JwtAuthenticationException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
