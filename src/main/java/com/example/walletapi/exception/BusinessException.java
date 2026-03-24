package com.example.walletapi.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 5742509543285295962L;

	public BusinessException(String message) {
        super(message);
    }
}
