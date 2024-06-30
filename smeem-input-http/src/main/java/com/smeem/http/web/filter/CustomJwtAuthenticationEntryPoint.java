package com.smeem.http.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.http.dto.response.ExceptionResponse;
import com.smeem.http.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        setResponse(response);
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(objectMapper.writeValueAsString(exceptionResponse()));
    }

    private ResponseEntity<ExceptionResponse> exceptionResponse() {
        return ResponseEntity
                .status(ExceptionCode.UNAUTHORIZED.getStatusCode())
                .body(ExceptionResponse.of(ExceptionCode.UNAUTHORIZED));
    }
}
