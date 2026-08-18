package com.Ali_Choopani.Task_Management_System.security.securityExceptionHandlers;

import com.Ali_Choopani.Task_Management_System.ErrorResponse;
import com.Ali_Choopani.Task_Management_System.exceptions.JwtAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static java.time.LocalDateTime.now;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorMessage = getReceivedErrorMessage(authException);

        log.warn(errorMessage);
        response.setContentType("application/json");
        response.setStatus(SC_UNAUTHORIZED);

        ErrorResponse errorResponse = new ErrorResponse(401, "Unauthorized", errorMessage, request.getRequestURI(), now());

        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }

    private String getReceivedErrorMessage(AuthenticationException authException) {
        String errorMessage = "";

        if (authException instanceof BadCredentialsException) {
            errorMessage = "Email\\Phone Number or Password is invalid !";

        } else if (authException instanceof UsernameNotFoundException ex) {
            errorMessage = ex.getMessage();

        } else if (authException instanceof JwtAuthenticationException ex) {
            errorMessage = ex.getMessage();
        } else if (authException instanceof InsufficientAuthenticationException) {
            errorMessage = "To access to the endpoint, you must register or login !";
        }

        return errorMessage;
    }
}
