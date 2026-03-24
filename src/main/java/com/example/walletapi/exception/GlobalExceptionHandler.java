package com.example.walletapi.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.walletapi.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponseDTO> handleBusiness(BusinessException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO("BUSINESS_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO("INTERNAL_ERROR", "Ocurrió un error inesperado");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {

	    String message = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(e -> e.getField() + ": " + e.getDefaultMessage())
	            .collect(Collectors.joining(", "));

	    ErrorResponseDTO error = new ErrorResponseDTO("VALIDATION_ERROR", message);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
