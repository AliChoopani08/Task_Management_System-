package com.Ali_Choopani.Task_Management_System;

import com.Ali_Choopani.Task_Management_System.exceptions.*;
import com.Ali_Choopani.Task_Management_System.exceptions.project.DuplicateProjectMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.project.NotFoundProjectAndMemberException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.DuplicateUsername;
import com.Ali_Choopani.Task_Management_System.exceptions.user.InsufficientInformationException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.NotFoundUserException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.UserWithRoleAndIdNotFoundException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.InvalidDeviceException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken.DuplicateRefreshTokenException;
import com.Ali_Choopani.Task_Management_System.exceptions.user.device.refreshToken.NotFoundRefreshTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlocalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidationDataHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errorMessage = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorMessage.put(error.getField(), error.getDefaultMessage()));

        return status(BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundUrlHandler(HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Invalid URL", "There isn't any endpoint with this URL !", request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> NotBeingMatchUrlWithMethodHandler(HttpServletRequest request) {
        final ErrorResponse errorResponse = new ErrorResponse(CONFLICT.value(), "Not Being Match",
                "The requested URL and method aren't match to each other !",
                format("requested URL: {%s} , requested method : {%s}", request.getRequestURI(), request.getMethod()), now());

        return status(CONFLICT)
                        .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> invalidUrlParametersHandler(HttpServletRequest request) {
        return getErrorResponse(BAD_REQUEST, "Invalid Parameters", "The Parameters of this URL are invalid", request);
    }



        @ExceptionHandler(DuplicateUsername.class)
    public ResponseEntity<ErrorResponse> duplicateUsernameHandler(DuplicateUsername ex, HttpServletRequest request) {
        return getErrorResponse(CONFLICT, "Duplicate Username", ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidDeviceException.class)
    public ResponseEntity<ErrorResponse> invalidDeviceHandler(InvalidDeviceException ex, HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Invalid Device", ex.getMessage(), request);
    }

    @ExceptionHandler(NotFoundSecretKey.class)
    public ResponseEntity<ErrorResponse> notFoundSecretKeyHandler(NotFoundSecretKey ex, HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Not Found Secret Key", ex.getMessage(), request);
    }

    @ExceptionHandler(InsufficientInformationException.class)
    public ResponseEntity<ErrorResponse> insufficientRegisterInformationHandler(InsufficientInformationException ex, HttpServletRequest request) {
        return getErrorResponse(BAD_REQUEST, "Insufficient Information", ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> duplicateRefreshTokenHandler(DuplicateRefreshTokenException ex, HttpServletRequest request) {
        return getErrorResponse(CONFLICT, "Duplicate Refresh Token", ex.getMessage(), request);
    }

    @ExceptionHandler(UserWithRoleAndIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundUserWithIdAndRoleHandler(UserWithRoleAndIdNotFoundException ex, HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Not Found User", ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateProjectMemberException.class)
    public ResponseEntity<ErrorResponse> duplicateMemberInProjectHandler(DuplicateProjectMemberException ex, HttpServletRequest request) {
        return getErrorResponse(CONFLICT, "Duplicate Member", ex.getMessage(), request);
    }

    @ExceptionHandler(NotFoundRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> notFoundRefreshTokenHandler(NotFoundRefreshTokenException ex, HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Not Found Refresh Token", ex.getMessage(), request);
    }

    @ExceptionHandler(NotFoundProjectAndMemberException.class)
    public ResponseEntity<ErrorResponse> NotFoundProjectAndMemberHandler(NotFoundProjectAndMemberException ex, HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorResponse> notFoundUserException(NotFoundUserException ex, HttpServletRequest request) {
        return getErrorResponse(NOT_FOUND, "Not Found", ex.getMessage(), request);
    }

        private ResponseEntity<ErrorResponse> getErrorResponse(HttpStatus status, String error, String message, HttpServletRequest request) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status.value(), error, message, request.getRequestURI(), now()));
    }


}
