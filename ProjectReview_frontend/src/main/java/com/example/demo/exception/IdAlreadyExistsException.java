package com.example.demo.exception;

public class IdAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdAlreadyExistsException() {
		super();
	}

	public IdAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IdAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdAlreadyExistsException(String message) {
		super(message);
	}

	public IdAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	

}
