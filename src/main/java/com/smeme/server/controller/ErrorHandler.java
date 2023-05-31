package com.smeme.server.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.NoSuchElementException;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smeme.server.util.ApiResponse;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiResponse> entityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.status(NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(ChangeSetPersister.NotFoundException.class)
	public ResponseEntity<ApiResponse> notFoundException(ChangeSetPersister.NotFoundException ex) {
		return ResponseEntity.status(NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ApiResponse> noSuchElementException(NoSuchElementException ex) {
		return ResponseEntity.status(NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse> constraintViolationException(ConstraintViolationException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<ApiResponse> entityExistsException(EntityExistsException ex) {
		return ResponseEntity.status(BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
	}

	@ExceptionHandler(InvalidBearerTokenException.class)
	public ResponseEntity<ApiResponse> invalidBearerTokenException(InvalidBearerTokenException ex) {
		return ResponseEntity.status(UNAUTHORIZED).body(ApiResponse.fail(ex.getMessage()));
	}
}
