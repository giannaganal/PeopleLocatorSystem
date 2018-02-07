package com.java.pointwest.exceptions;

public class ExpectedIntException extends Exception {

	private static final String ERROR_MESSAGE = "Expected integer but received a non-integer."; 
	
	public String getDisplayErrorMessage() {
		// TODO Auto-generated method stub
		return ERROR_MESSAGE;
	}

}
