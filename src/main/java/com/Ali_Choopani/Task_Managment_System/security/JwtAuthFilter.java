package com.Ali_Choopani.Task_Managment_System.security;

import com.Ali_Choopani.Task_Managment_System.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService detailsService;
    private final AuthenticationEntryPoint entryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);

        try {
            Long userId = jwtService.extractId(jwtToken);
            if (userId != null && getContext().getAuthentication() == null) {
                final UserDetails userDetail = detailsService.loadUserById(userId);
                final var authToken = new UsernamePasswordAuthenticationToken(userDetail, empty(), userDetail.getAuthorities());
                getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            handlerJwtExceptions(request, response, new JwtAuthenticationException("This JWT token has been expired !", ex));
        } catch (MalformedJwtException ex) {
            handlerJwtExceptions(request, response, new JwtAuthenticationException("This JWt token has an invalid format !", ex));
        } catch (SignatureException ex) {
            handlerJwtExceptions(request, response, new JwtAuthenticationException("The signature of this JWT token is invalid !", ex));
        }
    }

    private void handlerJwtExceptions(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws ServletException, IOException {
        SecurityContextHolder.clearContext();
        entryPoint.commence(request, response, ex);
    }
}
