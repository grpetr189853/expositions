package com.grpetr.task.exception;

public class DBException extends AppException {

	private static final long serialVersionUID = 8089221839507651787L;
	
	public DBException(){
		super();
	}

	public DBException(String message, Throwable cause){
		super(message, cause);
	}
	
	public DBException(String message){
		super(message);
	}
}
