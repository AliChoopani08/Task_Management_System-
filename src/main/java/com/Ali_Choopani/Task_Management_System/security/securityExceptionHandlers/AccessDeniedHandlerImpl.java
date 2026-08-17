package com.Ali_Choopani.Task_Management_System.security.securityExceptionHandlers;

import com.Ali_Choopani.Task_Management_System.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static java.time.LocalDateTime.now;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(SC_FORBIDDEN);

      log.warn("Authorization exception: message: {}", accessDeniedException.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Authorization Exception",
                "You don't have permission to access this endpoint !",request.getRequestURI(), now());

        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
