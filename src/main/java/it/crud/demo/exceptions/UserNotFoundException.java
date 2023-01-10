package it.crud.demo.exceptions;

public class UserNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1090123055138953224L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
