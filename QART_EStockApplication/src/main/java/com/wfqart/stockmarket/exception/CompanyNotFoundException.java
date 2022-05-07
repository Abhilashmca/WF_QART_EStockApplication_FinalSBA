package com.wfqart.stockmarket.exception;

public class CompanyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3156833551002324065L;

	public CompanyNotFoundException(String message) {
		super(message);
	}
}