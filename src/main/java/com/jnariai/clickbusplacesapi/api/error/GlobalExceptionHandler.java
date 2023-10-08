package com.jnariai.clickbusplacesapi.api.error;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler (MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleNotValid(MethodArgumentNotValidException exception) {
		List<FieldError> errors = exception.getBindingResult().getFieldErrors();
		StringJoiner errorJoiner = new StringJoiner("; ");
		errors.forEach(fieldError -> errorJoiner.add(fieldError.getDefaultMessage()));
		ErrorResponse errorResponse = new ErrorResponse(errorJoiner.toString(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler (EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	@ExceptionHandler (EntityExistsException.class)
	public ResponseEntity<ErrorResponse> handleEntityExists(EntityExistsException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT.value());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}

}