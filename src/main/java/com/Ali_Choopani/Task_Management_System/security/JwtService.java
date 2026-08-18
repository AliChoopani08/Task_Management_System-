package com.Ali_Choopani.Task_Management_System.security;

public interface JwtService {

    String generateToken(Long userId, String userRole);
    Long extractId(String token);
    boolean isTokenExpired(String token);
    boolean isTokenValid(String token, Long userId);
}
