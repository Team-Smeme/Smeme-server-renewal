package com.smeme.server.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smeme.server.util.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse> EntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> IllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}
}
