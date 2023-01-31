package it.crud.demo.exceptions;

@SuppressWarnings("serial")
public class IllegalPasswordException extends RuntimeException {

	public IllegalPasswordException(String message) {
		super(message);
	}
	
	

}
