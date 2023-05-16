package com.smeme.server.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.NoSuchElementException;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

import com.smeme.server.util.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse> EntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.status(NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponse> NotFoundException(NotFoundException ex) {
		return ResponseEntity.status(NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ApiResponse> NoSuchElementException(NoSuchElementException ex) {
		return ResponseEntity.status(NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> IllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse> ConstraintViolationException(ConstraintViolationException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<ApiResponse> EntityExistsException(EntityExistsException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}
}
