package com.wfqart.stockmarket.exception;

public class InvalidDateException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5767042651963989131L;

	public InvalidDateException(String message)	{
		super(message);
	}

}
