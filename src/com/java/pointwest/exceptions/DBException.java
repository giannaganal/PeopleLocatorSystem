package com.java.pointwest.exceptions;

public class DBException extends Exception {
	public static final String NO_CONNECTION = "No connection established. Please check the IP address, database name or credentials.";
	public static final String NO_CLASS = "Class is not found. Please make sure you have the correct spelling"
			+ " of the driver or have the jar file installed.";
	public static final String WRONG_DB_CREDENTIALS = "The database credentials are wrong. Please check the spelling of your database name"
			+ ", username and password.";
	public static final String GENERIC_ERROR_MESSAGE = "There is an error connecting in the database while ";
	public static final String CHECK_LOGS_MESSAGE = ". Please check the logs.";
	
	private String genericErrorMessage = "";
			
	public String getErrorMessage(String action) {
		return GENERIC_ERROR_MESSAGE + action + CHECK_LOGS_MESSAGE;
	}
}
