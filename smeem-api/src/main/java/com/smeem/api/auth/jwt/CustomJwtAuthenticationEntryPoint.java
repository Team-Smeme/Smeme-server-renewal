package com.smeem.api.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smeem.api.support.ApiResponseGenerator;
import com.smeem.api.common.FailureResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.smeem.common.code.failure.AuthFailureCode.INVALID_TOKEN;

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
        response.getWriter().println(objectMapper.writeValueAsString(getFailureResponse()));
    }

    private ResponseEntity<FailureResponse> getFailureResponse() {
        return ApiResponseGenerator.failure(INVALID_TOKEN);
    }
}
