package it.crud.demo.exceptions;

@SuppressWarnings("serial")
public class UserIdAlreadyExsistException extends RuntimeException {
	
	public UserIdAlreadyExsistException (String message) {
		super(message);
	}

}
