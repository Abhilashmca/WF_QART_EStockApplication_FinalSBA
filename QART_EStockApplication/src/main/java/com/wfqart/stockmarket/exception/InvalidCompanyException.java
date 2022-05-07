	package com.wfqart.stockmarket.exception;

public class InvalidCompanyException extends RuntimeException {

	private static final long serialVersionUID = 2119010801632373899L;

	public InvalidCompanyException(String message)	{
		super(message);
	}
}