package com.smeem.common.advicer;


import java.io.IOException;
import java.util.NoSuchElementException;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ErrorHandler {

//	@ExceptionHandler(EntityNotFoundException.class)
//	public ResponseEntity<ApiResponse> entityNotFoundException(EntityNotFoundException ex) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(ChangeSetPersister.NotFoundException.class)
//	public ResponseEntity<ApiResponse> notFoundException(ChangeSetPersister.NotFoundException ex) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(NoSuchElementException.class)
//	public ResponseEntity<ApiResponse> noSuchElementException(NoSuchElementException ex) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException ex) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<ApiResponse> constraintViolationException(ConstraintViolationException ex) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(EntityExistsException.class)
//	public ResponseEntity<ApiResponse> entityExistsException(EntityExistsException ex) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(InvalidBearerTokenException.class)
//	public ResponseEntity<ApiResponse> invalidBearerTokenException(InvalidBearerTokenException ex) {
//		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(IOException.class)
//	public ResponseEntity<ApiResponse> ioException(IOException ex) {
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ex.getMessage()));
//	}
//
//	@ExceptionHandler(IllegalStateException.class)
//	public ResponseEntity<ApiResponse> illegalStateException(IllegalStateException ex) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(ex.getMessage()));
//	}
}
